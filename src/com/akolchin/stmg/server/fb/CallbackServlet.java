package com.akolchin.stmg.server.fb;

import java.io.IOException;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.akolchin.stmg.server.ServerUtils;
import com.akolchin.stmg.server.authentication.CurrentAppUserProvider;
import com.akolchin.stmg.server.dao.AppUserDao;
import com.akolchin.stmg.server.dao.InviteDao;
import com.akolchin.stmg.shared.TooManyResultsException;
import com.akolchin.stmg.shared.domain.AppUser;
import com.akolchin.stmg.shared.domain.Invite;

import facebook4j.Facebook;
import facebook4j.FacebookException;

public class CallbackServlet extends HttpServlet {

	private static final Logger log = Logger.getLogger(CallbackServlet.class.getCanonicalName());

	@Inject
	protected CurrentAppUserProvider currentAppUserProvider;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();

		Facebook facebook = (Facebook) session.getAttribute("facebook");
		String oauthCode = request.getParameter("code");

		try {
			facebook.getOAuthAccessToken(oauthCode);
			session.setAttribute("facebook", facebook);

			String email = facebook.getMe().getEmail(); // about.getEmail();

			AppUserDao appUserDao = new AppUserDao();
			AppUser user = appUserDao.findByEmail(email);

			if (user == null) { // new user -> sign up him

				user = new AppUser();
				user.setEmail(email);
				user.setName(facebook.getName());
				user.setCash(10000d);

				// if user was invited - link him as affiliated to the inviter
				InviteDao inviteDao = new InviteDao();
				Invite invited = inviteDao.getByFbId(facebook.getId());
				if (invited != null)
					user.setInviter(invited.getInviter());

				user = appUserDao.saveAndReturn(user);

				log.fine("new User saved:  " + user);
				
				inviteDao.delete(invited);
				//TODO do all in one transaction
			}

			if (user != null)
				currentAppUserProvider.set(user);

		} catch (FacebookException e) {
			log.warning(e.getErrorMessage());
			throw new ServletException(e);
		} catch (TooManyResultsException e) {
			log.warning("Duplicated users with the same email" + e.getMessage());
			throw new RuntimeException("Duplicated users with the same email" + e.getMessage(), e);
		}

		// TODO implement different URL for production context
		response.sendRedirect(request.getContextPath()
				+ (ServerUtils.isProduction() ? "/" : "/Project.html?gwt.codesvr=127.0.0.1:9997"));
	}
}
