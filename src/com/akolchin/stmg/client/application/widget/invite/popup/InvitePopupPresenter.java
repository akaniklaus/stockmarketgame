package com.akolchin.stmg.client.application.widget.invite.popup;

import java.util.Map;

import com.akolchin.stmg.client.application.affiliates.AffiliatesPresenter;
import com.akolchin.stmg.client.dispatch.MyServerAsyncCallbackImpl;
import com.akolchin.stmg.shared.action.GetFbFriendsAction;
import com.akolchin.stmg.shared.action.GetFbFriendsResult;
import com.akolchin.stmg.shared.action.InviteFbFriendAction;
import com.akolchin.stmg.shared.action.InviteFbFriendResult;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.dispatch.rpc.shared.DispatchAsync;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PopupView;
import com.gwtplatform.mvp.client.PresenterWidget;

public class InvitePopupPresenter extends PresenterWidget<InvitePopupPresenter.MyView> implements InvitePopupUiHandlers {
	public interface Factory {
		InvitePopupPresenter create(AffiliatesPresenter parentPresenter);
	}

	public interface MyView extends PopupView, HasUiHandlers<InvitePopupUiHandlers> {

		void displayFriends(Map<String, String> friends);
	}

	private DispatchAsync dispatchAsync;

	private AffiliatesPresenter parentPresenter;

	@Inject
	InvitePopupPresenter(final EventBus eventBus, final MyView view, final DispatchAsync dispatchAsync,
			@Assisted AffiliatesPresenter parentPresenter) {
		super(eventBus, view);

		this.parentPresenter = parentPresenter;

		this.dispatchAsync = dispatchAsync;

		getView().setUiHandlers(this);
	}

	@Override
	protected void onReveal() {
		super.onReveal();

		dispatchAsync.execute(new GetFbFriendsAction(), new MyServerAsyncCallbackImpl<GetFbFriendsResult>() {

			@Override
			public void onSuccess(GetFbFriendsResult result) {
				getView().displayFriends(result.getFriends());

			}
		});
	}

	@Override
	public void onInviteButtonClick(String invitedId, String invitedName) {
		dispatchAsync.execute(new InviteFbFriendAction(invitedId, invitedName),
				new MyServerAsyncCallbackImpl<InviteFbFriendResult>() {

					@Override
					public void onSuccess(InviteFbFriendResult result) {
						if (result.getInvited() != null)
							parentPresenter.displayInvite(result.getInvited());
					}

				});

	}

}
