package com.akolchin.stmg.server.dispatch;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import com.akolchin.stmg.server.authentication.CurrentAppUserProvider;
import com.akolchin.stmg.server.dao.ShareDao;
import com.akolchin.stmg.server.dispatch.auth.AbstractHandlerWithAuth;
import com.akolchin.stmg.shared.action.GetPortfolioAction;
import com.akolchin.stmg.shared.action.GetPortfolioResult;
import com.akolchin.stmg.shared.domain.Share;
import com.gwtplatform.dispatch.rpc.server.ExecutionContext;
import com.gwtplatform.dispatch.shared.ActionException;

public class GetPortfolioHandler extends AbstractHandlerWithAuth<GetPortfolioAction, GetPortfolioResult> {

	@Inject
	public GetPortfolioHandler(CurrentAppUserProvider currentAppUserProvider) {
		super(GetPortfolioAction.class, currentAppUserProvider);
	}

	@Override
	public GetPortfolioResult execute(GetPortfolioAction action, ExecutionContext context) throws ActionException {

		List<Share> shares = new ArrayList<Share>(new ShareDao().listAll(getCurrenAppUser()));
		return new GetPortfolioResult(shares);
	}

}
