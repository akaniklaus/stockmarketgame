package com.akolchin.stmg.server.dispatch.auth;

import com.akolchin.stmg.server.ServerUtils;
import com.akolchin.stmg.server.authentication.CurrentAppUserProvider;
import com.akolchin.stmg.server.dispatch.MyAbstractActionHandler;
import com.akolchin.stmg.shared.domain.AppUser;
import com.akolchin.stmg.shared.dto.UserDto;
import com.gwtplatform.dispatch.rpc.shared.Action;
import com.gwtplatform.dispatch.rpc.shared.Result;
import com.gwtplatform.dispatch.shared.ActionException;

public abstract class AbstractHandlerWithAuth<A extends Action<R>, R extends Result> extends
		MyAbstractActionHandler<A, R> {

	protected final CurrentAppUserProvider currentAppUserProvider;

	public AbstractHandlerWithAuth(Class<A> actionType, final CurrentAppUserProvider currentAppUserProvider) {
		super(actionType);

		this.currentAppUserProvider = currentAppUserProvider;
	}

	protected UserDto getCurrentUserDto(AppUser appUser) throws ActionException {
		currentAppUserProvider.set(appUser);

		return getCurrentUserDto();
	}

	protected UserDto getCurrentUserDto() throws ActionException {
		AppUser appUser = getCurrenAppUser();

		if (appUser != null) // logged in user
			return ServerUtils.getUserDto(true, appUser);
		else
			// stab for not logged in user
			return new UserDto();
	}

	protected AppUser getCurrenAppUser() throws ActionException {
		if (currentAppUserProvider == null)
			throw new ActionException("currentAppUserProvider is null");
		AppUser appUser = currentAppUserProvider.get();
		return appUser;
	}
}