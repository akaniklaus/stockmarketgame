package com.akolchin.stmg.server.dispatch.auth;

import javax.inject.Inject;

import com.akolchin.stmg.server.authentication.CurrentAppUserProvider;
import com.akolchin.stmg.shared.action.LogoutAction;
import com.akolchin.stmg.shared.action.LogoutResult;
import com.gwtplatform.dispatch.rpc.server.ExecutionContext;
import com.gwtplatform.dispatch.shared.ActionException;

public class LogoutHandler extends AbstractHandlerWithAuth<LogoutAction, LogoutResult> {

	@Inject
	public LogoutHandler(final CurrentAppUserProvider currentAppUserProvider) {
		super(LogoutAction.class, currentAppUserProvider);
	}

	@Override
	public LogoutResult execute(LogoutAction action, ExecutionContext context) throws ActionException {
		currentAppUserProvider.set(null);
		return new LogoutResult(getCurrentUserDto());
	}
}
