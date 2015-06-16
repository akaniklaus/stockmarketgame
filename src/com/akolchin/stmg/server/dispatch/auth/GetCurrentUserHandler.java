package com.akolchin.stmg.server.dispatch.auth;

import javax.inject.Inject;

import com.akolchin.stmg.server.authentication.CurrentAppUserProvider;
import com.akolchin.stmg.shared.action.GetCurrentUserAction;
import com.akolchin.stmg.shared.action.GetCurrentUserResult;
import com.gwtplatform.dispatch.rpc.server.ExecutionContext;
import com.gwtplatform.dispatch.shared.ActionException;

public class GetCurrentUserHandler extends AbstractHandlerWithAuth<GetCurrentUserAction, GetCurrentUserResult> {

	@Inject
	public GetCurrentUserHandler(CurrentAppUserProvider currentAppUserProvider) {
		super(GetCurrentUserAction.class, currentAppUserProvider);
	}

	@Override
	public GetCurrentUserResult execute(GetCurrentUserAction action, ExecutionContext context) throws ActionException {
		return new GetCurrentUserResult(getCurrentUserDto());
	}

}
