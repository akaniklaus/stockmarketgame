package com.akolchin.stmg.client.application.widget.login;

import javax.inject.Inject;

import com.akolchin.stmg.client.event.LoginAuthenticatedEvent;
import com.akolchin.stmg.client.place.NameTokens;
import com.akolchin.stmg.shared.dto.UserDto;
import com.google.gwt.user.client.Window;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.dispatch.rpc.shared.DispatchAsync;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.shared.proxy.PlaceRequest.Builder;

public class LoginPresenter extends PresenterWidget<LoginPresenter.MyView> implements LoginUiHandlers {
	@Inject
	DispatchAsync dispatchAsync;

	@Inject
	PlaceManager placeManager;

	public interface MyView extends View, HasUiHandlers<LoginUiHandlers> {

	}

	private final UserDto currentUser;

	@Inject
	public LoginPresenter(final EventBus eventBus, final MyView view, final UserDto currentUser) {
		super(eventBus, view);

		this.currentUser = currentUser;

		getView().setUiHandlers(this);
	}

	@Override
	protected void onReveal() {
		super.onReveal();

		reveal();
	}

	@Override
	public void onSigninWithFB() {
		Window.Location.replace("/fb_signin");
	}

	private void reveal() { // TODO move to login module

		if (currentUser.isLoggedIn()) {
			getEventBus().fireEvent(new LoginAuthenticatedEvent(currentUser));
			placeManager.revealPlace(new Builder().nameToken(NameTokens.getApplication()).build());
		}
	}

}
