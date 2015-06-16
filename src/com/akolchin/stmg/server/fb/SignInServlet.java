package com.akolchin.stmg.server.fb;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.akolchin.stmg.server.ServerUtils;

import facebook4j.Facebook;
import facebook4j.FacebookFactory;
import facebook4j.conf.Configuration;
import facebook4j.conf.ConfigurationBuilder;

public class SignInServlet extends HttpServlet {
	private static final long serialVersionUID = -7453606094644144082L;

	private static final Logger log = Logger.getLogger(SignInServlet.class.getCanonicalName());

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		Facebook facebook = new FacebookFactory(getConfiguration()).getInstance();
		request.getSession().setAttribute("facebook", facebook);

		StringBuffer callbackURL = request.getRequestURL();
		int index = callbackURL.lastIndexOf("/");
		callbackURL.replace(index, callbackURL.length(), "").append("/fb_callback");
		response.sendRedirect(facebook.getOAuthAuthorizationURL(callbackURL.toString()));
	}

	private static final Configuration getConfiguration() {
		ConfigurationBuilder cb = new ConfigurationBuilder();
		if (ServerUtils.isProduction())
			cb.setDebugEnabled(true).setOAuthAppId("1413372402248352")
					.setOAuthAppSecret("ad682d030f68ac1f6cc75da2e91dc6a0");
		else
			// local
			cb.setDebugEnabled(true).setOAuthAppId("341957189286131")
					.setOAuthAppSecret("088f5d7274c07d73587465b85ad808c7");

		return cb.setOAuthPermissions("email,publish_stream").setJSONStoreEnabled(true).build();
	}
}
