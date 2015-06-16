package com.akolchin.stmg.server.dispatch;

import java.util.List;

import javax.inject.Inject;

import com.akolchin.stmg.server.ServerUtils;
import com.akolchin.stmg.server.authentication.CurrentAppUserProvider;
import com.akolchin.stmg.server.dao.AppUserDao;
import com.akolchin.stmg.server.dispatch.auth.AbstractHandlerWithAuth;
import com.akolchin.stmg.shared.action.GetRanksAction;
import com.akolchin.stmg.shared.action.GetRanksResult;
import com.akolchin.stmg.shared.domain.AppUser;
import com.gwtplatform.dispatch.rpc.server.ExecutionContext;
import com.gwtplatform.dispatch.shared.ActionException;

public class GetRanksHandler extends AbstractHandlerWithAuth<GetRanksAction, GetRanksResult> {

	@Inject
	public GetRanksHandler(CurrentAppUserProvider currentAppUserProvider) {
		super(GetRanksAction.class, currentAppUserProvider);
	}

	@Override
	public GetRanksResult execute(GetRanksAction action, ExecutionContext context) throws ActionException {

		List<AppUser> topRankedUsers = new AppUserDao().listTopByRank();

		return new GetRanksResult(ServerUtils.getUserDtos(topRankedUsers));
	}

}
