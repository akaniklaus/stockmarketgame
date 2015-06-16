package com.akolchin.stmg.client.application.user;

import com.akolchin.stmg.client.application.ApplicationPresenter;
import com.akolchin.stmg.client.dispatch.MyServerAsyncCallbackImpl;
import com.akolchin.stmg.client.event.PortfolioChangedEvent;
import com.akolchin.stmg.client.event.PortfolioChangedEvent.PortfolioChangedHandler;
import com.akolchin.stmg.client.place.NameTokens;
import com.akolchin.stmg.shared.action.GetCurrentUserAction;
import com.akolchin.stmg.shared.action.GetCurrentUserResult;
import com.akolchin.stmg.shared.dto.UserDto;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.dispatch.rpc.shared.DispatchAsync;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;

public class UserPresenter extends Presenter<UserPresenter.MyView, UserPresenter.MyProxy> implements
		PortfolioChangedHandler {
	interface MyView extends View {

		void displayName(String string);

		void displayCash(Double cash);

		void displayValue(Double value);

		void displayReturnPct(Double returnPct);
	}

	@NameToken(NameTokens.user)
	@ProxyCodeSplit
	public interface MyProxy extends ProxyPlace<UserPresenter> {
	}

	private final UserDto currentUser;
	private final DispatchAsync dispatchAsync;

	@Inject
	public UserPresenter(EventBus eventBus, MyView view, MyProxy proxy, UserDto currentUser,
			DispatchAsync dispatchAsync) {
		super(eventBus, view, proxy, ApplicationPresenter.SLOT_User);

		this.currentUser = currentUser;

		this.dispatchAsync = dispatchAsync;

	}

	@Override
	protected void onBind() {
		super.onBind();

		addHandler(PortfolioChangedEvent.getType(), this);
	}

	@Override
	protected void onReset() {
		super.onReset();

		refresh();
	}

	@Override
	public void onPortfolioChanged(PortfolioChangedEvent event) {
		// re get current user from the server and display values
		dispatchAsync.execute(new GetCurrentUserAction(), new MyServerAsyncCallbackImpl<GetCurrentUserResult>() {
			@Override
			public void onSuccess(GetCurrentUserResult result) {
				currentUser.copyFrom(result.getCurrentUserDto());
				refresh();
			}

		});

	}

	private void refresh() {
		getView().displayName(currentUser.getName() + " (" + currentUser.getEmail() + ")");
		getView().displayCash(currentUser.getCash());
		getView().displayValue(currentUser.getValue());
		getView().displayReturnPct(currentUser.getReturnPct());
	}

}
