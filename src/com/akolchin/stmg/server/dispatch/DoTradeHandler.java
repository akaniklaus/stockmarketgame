package com.akolchin.stmg.server.dispatch;

import java.util.logging.Logger;

import javax.inject.Inject;

import com.akolchin.stmg.server.authentication.CurrentAppUserProvider;
import com.akolchin.stmg.server.dao.AppUserDao;
import com.akolchin.stmg.server.dao.ShareDao;
import com.akolchin.stmg.server.dispatch.auth.AbstractHandlerWithAuth;
import com.akolchin.stmg.shared.TooManyResultsException;
import com.akolchin.stmg.shared.action.DoTradeAction;
import com.akolchin.stmg.shared.action.DoTradeResult;
import com.akolchin.stmg.shared.domain.AppUser;
import com.akolchin.stmg.shared.domain.Share;
import com.gwtplatform.dispatch.rpc.server.ExecutionContext;
import com.gwtplatform.dispatch.shared.ActionException;

public class DoTradeHandler extends AbstractHandlerWithAuth<DoTradeAction, DoTradeResult> {
	private static final Logger log = Logger.getLogger(DoTradeHandler.class.getCanonicalName());

	@Inject
	public DoTradeHandler(CurrentAppUserProvider currentAppUserProvider) {
		super(DoTradeAction.class, currentAppUserProvider);
	}

	@Override
	public DoTradeResult execute(DoTradeAction action, ExecutionContext context) throws ActionException {
		ShareDao shareDao = new ShareDao();
		AppUser user = getCurrenAppUser();
		String symbol = action.getSymbol();

		try {
			Share share = shareDao.getBySymbol(symbol, user);
			if (share != null) {// share of stock with such symbol exist in user portfolio

				Double amount = action.getAmount();
				Double value = amount * share.getStock().getLastTradePrice();

				// TODO Share and User cashe updates should be in one transaction ---
				// TODO see https://code.google.com/p/objectify-appengine/wiki/Transactions

				switch (action.getAction()) {
				case BUY:
					Double cashRemain = user.getCash() - value;
					if (cashRemain >= 0) { // enough cash to buy
						share.setShares(share.getShares() + amount);
						user.setCash(cashRemain);
					} else {
						log.warning("BUY for " + user.getEmail() + " not performed: not enough cash");
						return new DoTradeResult(false);
					}

					break;

				case SELL:
					Double sharesRemain = share.getShares() - amount;
					if (sharesRemain >= 0) {// enough shares to sell
						share.setShares(sharesRemain);
						user.setCash(user.getCash() + value);
					} else {
						log.warning("SELL for " + user.getEmail() + " not performed: not enough shares");
						return new DoTradeResult(false);
					}

					break;
				}

				shareDao.save(share);
				new AppUserDao().save(user);

				// TODO here should be transaction END ---

				log.finer(action.toString() + " Performed successfully");
				return new DoTradeResult(true);

			} else
				log.info(action.getSymbol() + " symbol doesnot exist in portfolio");

		} catch (TooManyResultsException e) {
			// TODO understand how I have deal with such error
			log.warning("duplicated symbol record ");
		}

		return new DoTradeResult(false);

	}
}
