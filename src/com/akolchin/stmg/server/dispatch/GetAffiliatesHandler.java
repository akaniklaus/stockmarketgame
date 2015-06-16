package com.akolchin.stmg.server.dispatch;

import java.util.List;

import javax.inject.Inject;

import com.akolchin.stmg.server.ServerUtils;
import com.akolchin.stmg.server.authentication.CurrentAppUserProvider;
import com.akolchin.stmg.server.dao.AppUserDao;
import com.akolchin.stmg.server.dispatch.auth.AbstractHandlerWithAuth;
import com.akolchin.stmg.shared.action.GetAffiliatesAction;
import com.akolchin.stmg.shared.action.GetAffiliatesResult;
import com.akolchin.stmg.shared.domain.AppUser;
import com.gwtplatform.dispatch.rpc.server.ExecutionContext;
import com.gwtplatform.dispatch.shared.ActionException;

public class GetAffiliatesHandler extends AbstractHandlerWithAuth<GetAffiliatesAction, GetAffiliatesResult> {

	@Inject
	public GetAffiliatesHandler(CurrentAppUserProvider currentAppUserProvider) {
		super(GetAffiliatesAction.class, currentAppUserProvider);
	}

	@Override
	public GetAffiliatesResult execute(GetAffiliatesAction action, ExecutionContext context) throws ActionException {

		List<AppUser> affiliatedUsers = new AppUserDao().listAll(getCurrenAppUser());

		return new GetAffiliatesResult(ServerUtils.getUserDtos(affiliatedUsers));
	}

}
