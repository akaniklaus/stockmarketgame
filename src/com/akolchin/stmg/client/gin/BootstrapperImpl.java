package com.akolchin.stmg.client.gin;

import javax.inject.Inject;

import com.akolchin.stmg.client.dispatch.MyServerAsyncCallbackImpl;
import com.akolchin.stmg.client.place.NameTokens;
import com.akolchin.stmg.shared.action.GetCurrentUserAction;
import com.akolchin.stmg.shared.action.GetCurrentUserResult;
import com.akolchin.stmg.shared.dto.UserDto;
import com.gwtplatform.dispatch.rpc.shared.DispatchAsync;
import com.gwtplatform.mvp.client.Bootstrapper;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.shared.proxy.PlaceRequest.Builder;

public class BootstrapperImpl implements Bootstrapper {
	private final PlaceManager placeManager;
	private final DispatchAsync dispatcher;

	private final UserDto currentUser;

	@Inject
	public BootstrapperImpl(final PlaceManager placeManager, final DispatchAsync dispatcher,
			final UserDto currentUser) {
		this.placeManager = placeManager;
		this.dispatcher = dispatcher;
		this.currentUser = currentUser;
	}

	@Override
	public void onBootstrap() {
		getCurrentUser();
	}

	private void getCurrentUser() {

		dispatcher.execute(new GetCurrentUserAction(), new MyServerAsyncCallbackImpl<GetCurrentUserResult>() {
			@Override
			public void onSuccess(GetCurrentUserResult result) {
				onGetCurrentUserSuccess(result.getCurrentUserDto());
			}

			@Override
			public void onFailure(Throwable caught) {
				super.onFailure(caught);
				placeManager.revealCurrentPlace();
			}
		});
	}

	private void onGetCurrentUserSuccess(UserDto currentUserDto) {

		currentUser.copyFrom(currentUserDto);
		placeManager.revealCurrentPlace();

		// TODO skip login
		if (currentUser.isLoggedIn()) {
			// getEventBus().fireEvent(new LoginAuthenticatedEvent(currentUser));
			placeManager.revealPlace(new Builder().nameToken(NameTokens.getApplication()).build());
		}

	}

}
