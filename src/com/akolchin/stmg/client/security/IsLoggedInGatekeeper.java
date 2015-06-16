package com.akolchin.stmg.client.security;

import javax.inject.Inject;

import com.akolchin.stmg.client.event.LoginAuthenticatedEvent;
import com.akolchin.stmg.client.event.LoginResetEvent;
import com.akolchin.stmg.shared.dto.UserDto;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.proxy.Gatekeeper;

public class IsLoggedInGatekeeper implements Gatekeeper {
	private final EventBus eventBus;
	private UserDto currentUser;

	@Inject
	public IsLoggedInGatekeeper(final EventBus eventBus) {
		this.eventBus = eventBus;

		this.eventBus.addHandler(LoginAuthenticatedEvent.getType(),
				new LoginAuthenticatedEvent.LoginAuthenticatedHandler() {

					@Override
					public void onLoginAuthenticated(LoginAuthenticatedEvent event) {

						currentUser = event.getCurrentUser();
					}
				});

		this.eventBus.addHandler(LoginResetEvent.getType(), new LoginResetEvent.LoginResetHandler() {

			@Override
			public void onLoginReset(LoginResetEvent event) {

				currentUser = null;
			}
		});

	}

	@Override
	public boolean canReveal() {
		boolean loggedIn = false;

		if (currentUser != null)
			loggedIn = currentUser.isLoggedIn();

		return loggedIn;
	}

	public UserDto getCurrentUser() {
		return currentUser;
	}

}