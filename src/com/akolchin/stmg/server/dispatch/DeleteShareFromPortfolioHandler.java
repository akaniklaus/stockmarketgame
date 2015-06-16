package com.akolchin.stmg.server.dispatch;

import java.util.logging.Logger;

import javax.inject.Inject;

import com.akolchin.stmg.server.authentication.CurrentAppUserProvider;
import com.akolchin.stmg.server.dao.ShareDao;
import com.akolchin.stmg.server.dispatch.auth.AbstractHandlerWithAuth;
import com.akolchin.stmg.shared.TooManyResultsException;
import com.akolchin.stmg.shared.action.DeleteShareFromPortfolioAction;
import com.akolchin.stmg.shared.action.DeleteShareFromPortfolioResult;
import com.akolchin.stmg.shared.domain.AppUser;
import com.akolchin.stmg.shared.domain.Share;
import com.gwtplatform.dispatch.rpc.server.ExecutionContext;
import com.gwtplatform.dispatch.shared.ActionException;

public class DeleteShareFromPortfolioHandler extends
		AbstractHandlerWithAuth<DeleteShareFromPortfolioAction, DeleteShareFromPortfolioResult> {
	private static final Logger log = Logger.getLogger(DeleteShareFromPortfolioHandler.class.getCanonicalName());

	@Inject
	public DeleteShareFromPortfolioHandler(CurrentAppUserProvider currentAppUserProvider) {
		super(DeleteShareFromPortfolioAction.class, currentAppUserProvider);
	}

	@Override
	public DeleteShareFromPortfolioResult execute(DeleteShareFromPortfolioAction action, ExecutionContext context)
			throws ActionException {
		ShareDao shareDao = new ShareDao();

		AppUser user = getCurrenAppUser();
		String symbol = action.getSymbol();

		try {
			Share share = shareDao.getBySymbol(symbol, user);
			if (share != null) {
				// share of stock with such symbol exist in user portfolio
				if (share.getShares() == 0) {
					shareDao.delete(share);

					log.finer(symbol + " share deleted from " + user.getEmail() + " portfolio");
					return new DeleteShareFromPortfolioResult(true);
				} else
					log.finer(symbol + " share cann't be deleted from " + user.getEmail()
							+ " portfolio since its amount not null ");

			} else
				log.info(symbol + " symbol doesnot exist in " + user.getEmail() + "portfolio");

		} catch (TooManyResultsException e) {
			// TODO understand how I have deal with such error
			log.warning("duplicated symbol record ");
		}

		return new DeleteShareFromPortfolioResult(false);
	}
}
