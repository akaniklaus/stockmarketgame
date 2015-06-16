package com.akolchin.stmg.server.dispatch;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Inject;

import com.akolchin.stmg.server.authentication.CurrentAppUserProvider;
import com.akolchin.stmg.server.dao.ShareDao;
import com.akolchin.stmg.server.dispatch.auth.AbstractHandlerWithAuth;
import com.akolchin.stmg.shared.TooManyResultsException;
import com.akolchin.stmg.shared.action.AddStockToPortfolioAction;
import com.akolchin.stmg.shared.action.AddStockToPortfolioResult;
import com.akolchin.stmg.shared.domain.AppUser;
import com.akolchin.stmg.shared.domain.Share;
import com.gwtplatform.dispatch.rpc.server.ExecutionContext;
import com.gwtplatform.dispatch.shared.ActionException;

public class AddStockToPortfolioHandler extends
		AbstractHandlerWithAuth<AddStockToPortfolioAction, AddStockToPortfolioResult> {
	private static final Logger log = Logger.getLogger(AddStockToPortfolioHandler.class.getCanonicalName());

	@Inject
	public AddStockToPortfolioHandler(CurrentAppUserProvider currentAppUserProvider) {
		super(AddStockToPortfolioAction.class, currentAppUserProvider);
	}

	@Override
	public AddStockToPortfolioResult execute(AddStockToPortfolioAction action, ExecutionContext context)
			throws ActionException {
		ShareDao shareDao = new ShareDao();

		AppUser user = getCurrenAppUser();
		String symbol = action.getSymbol();

		try {
			if (shareDao.getBySymbol(symbol, user) == null) {
				// not yet added to user portfolio
				Share share = new Share();
				share.setSymbol(symbol);
				share.setUser(user);
				// TODO fill other fields

				share = shareDao.saveAndReturn(share);

				log.log(Level.FINER, symbol + " symbol added to " + user.getEmail() + " portfolio");
				return new AddStockToPortfolioResult(share);
			} else {
				log.log(Level.FINER, symbol + " symbol already added to " + user.getEmail() + " portfolio");
				return new AddStockToPortfolioResult(null);
			}

		} catch (TooManyResultsException e) {
			// TODO understand how I have deal with such error
			log.log(Level.WARNING, "duplicated symbol record ", e);
			return new AddStockToPortfolioResult(null);

		}

	}
}
