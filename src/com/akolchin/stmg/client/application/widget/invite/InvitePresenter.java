package com.akolchin.stmg.client.application.widget.invite;

import com.akolchin.stmg.client.application.affiliates.AffiliatesPresenter;
import com.akolchin.stmg.client.dispatch.MyServerAsyncCallbackImpl;
import com.akolchin.stmg.shared.action.DeleteInviteAction;
import com.akolchin.stmg.shared.action.DeleteInviteResult;
import com.akolchin.stmg.shared.domain.Invite;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.dispatch.rpc.shared.DispatchAsync;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;

public class InvitePresenter extends PresenterWidget<InvitePresenter.MyView> implements InviteUiHandlers {
	public interface Factory {
		InvitePresenter create(AffiliatesPresenter parentPresenter);
	}

	public interface MyView extends View, HasUiHandlers<InviteUiHandlers> {

		void displayName(String name);
	}

	private DispatchAsync dispatchAsync;

	private AffiliatesPresenter parentPresenter;

	private Invite invite;

	@Inject
	InvitePresenter(EventBus eventBus, MyView view, DispatchAsync dispatchAsync,
			@Assisted AffiliatesPresenter parentPresenter) {
		super(eventBus, view);

		this.parentPresenter = parentPresenter;

		this.dispatchAsync = dispatchAsync;

		getView().setUiHandlers(this);
	}

	public void setAndDisplay(Invite invite) {
		this.invite = invite;

		getView().displayName(invite.getFbName());
	}

	@Override
	public void onDeleteButtonClick() {
		dispatchAsync.execute(new DeleteInviteAction(invite.getId()),
				new MyServerAsyncCallbackImpl<DeleteInviteResult>() {

					@Override
					public void onSuccess(DeleteInviteResult result) {
						if (result.getIsDeleted())
							parentPresenter.removeInvite(invite.getId());

					}
				});

	}
}
