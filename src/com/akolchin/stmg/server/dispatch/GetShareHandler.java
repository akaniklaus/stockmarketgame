package com.akolchin.stmg.server.dispatch;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Inject;

import com.akolchin.stmg.server.authentication.CurrentAppUserProvider;
import com.akolchin.stmg.server.dao.ShareDao;
import com.akolchin.stmg.server.dispatch.auth.AbstractHandlerWithAuth;
import com.akolchin.stmg.shared.TooManyResultsException;
import com.akolchin.stmg.shared.action.GetShareAction;
import com.akolchin.stmg.shared.action.GetShareResult;
import com.akolchin.stmg.shared.domain.Share;
import com.gwtplatform.dispatch.rpc.server.ExecutionContext;
import com.gwtplatform.dispatch.shared.ActionException;

public class GetShareHandler extends AbstractHandlerWithAuth<GetShareAction, GetShareResult> {
	private static final Logger log = Logger.getLogger(GetShareHandler.class.getCanonicalName());

	@Inject
	public GetShareHandler(CurrentAppUserProvider currentAppUserProvider) {
		super(GetShareAction.class, currentAppUserProvider);
	}

	@Override
	public GetShareResult execute(GetShareAction action, ExecutionContext context) throws ActionException {

		Share share = null;
		try {
			share = new ShareDao().getBySymbol(action.getSymbol(), getCurrenAppUser());
		} catch (TooManyResultsException e) {
			// TODO understand how I have deal with such error
			log.log(Level.WARNING, "duplicated symbol record ", e);
		}
		return new GetShareResult(share);
	}

}
