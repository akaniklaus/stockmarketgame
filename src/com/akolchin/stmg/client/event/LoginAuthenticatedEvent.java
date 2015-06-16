package com.akolchin.stmg.client.event;

import com.akolchin.stmg.shared.dto.UserDto;
import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HasHandlers;

public class LoginAuthenticatedEvent extends GwtEvent<LoginAuthenticatedEvent.LoginAuthenticatedHandler> {

	public static Type<LoginAuthenticatedHandler> TYPE = new Type<LoginAuthenticatedHandler>();

	private final UserDto currentUser;

	public interface LoginAuthenticatedHandler extends EventHandler {
		void onLoginAuthenticated(LoginAuthenticatedEvent event);
	}

	public LoginAuthenticatedEvent(UserDto currentUser) {
		this.currentUser = currentUser;
	}

	@Override
	protected void dispatch(LoginAuthenticatedHandler handler) {
		handler.onLoginAuthenticated(this);
	}

	@Override
	public Type<LoginAuthenticatedHandler> getAssociatedType() {
		return TYPE;
	}

	public static Type<LoginAuthenticatedHandler> getType() {
		return TYPE;
	}

	public static void fire(HasHandlers source, UserDto currentUser) {
		source.fireEvent(new LoginAuthenticatedEvent(currentUser));
	}

	public UserDto getCurrentUser() {
		return currentUser;
	}
}
