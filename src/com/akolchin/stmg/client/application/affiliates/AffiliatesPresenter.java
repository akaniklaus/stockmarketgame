package com.akolchin.stmg.client.application.affiliates;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Provider;

import com.akolchin.stmg.client.application.ApplicationPresenter;
import com.akolchin.stmg.client.application.widget.invite.InvitePresenter;
import com.akolchin.stmg.client.application.widget.invite.popup.InvitePopupPresenter;
import com.akolchin.stmg.client.application.widget.otheruser.OtherUserPresenter;
import com.akolchin.stmg.client.dispatch.MyClientAsyncCallbackImpl;
import com.akolchin.stmg.client.dispatch.MyServerAsyncCallbackImpl;
import com.akolchin.stmg.client.event.PortfolioChangedEvent;
import com.akolchin.stmg.client.event.PortfolioChangedEvent.PortfolioChangedHandler;
import com.akolchin.stmg.client.place.NameTokens;
import com.akolchin.stmg.shared.action.GetAffiliatesAction;
import com.akolchin.stmg.shared.action.GetAffiliatesResult;
import com.akolchin.stmg.shared.action.GetInvitesAction;
import com.akolchin.stmg.shared.action.GetInvitesResult;
import com.akolchin.stmg.shared.domain.Invite;
import com.akolchin.stmg.shared.dto.UserDto;
import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.common.client.IndirectProvider;
import com.gwtplatform.common.client.StandardProvider;
import com.gwtplatform.dispatch.rpc.shared.DispatchAsync;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.ContentSlot;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.Proxy;
import com.gwtplatform.mvp.client.proxy.RevealContentHandler;

public class AffiliatesPresenter extends Presenter<AffiliatesPresenter.MyView, AffiliatesPresenter.MyProxy> implements
		AffiliatesUiHandlers, PortfolioChangedHandler {
	interface MyView extends View, HasUiHandlers<AffiliatesUiHandlers> {
	}

	@ContentSlot
	public static final Type<RevealContentHandler<?>> SLOT_Invites = new Type<RevealContentHandler<?>>();
	private final Map<Long, InvitePresenter> idToPresenterMap = new HashMap<Long, InvitePresenter>();

	@ContentSlot
	public static final Type<RevealContentHandler<?>> SLOT_Affiliates = new Type<RevealContentHandler<?>>();

	@NameToken(NameTokens.affiliates)
	@ProxyCodeSplit
	public interface MyProxy extends Proxy<AffiliatesPresenter> {
	}

	final IndirectProvider<OtherUserPresenter> otherUserPresenterProvider;

	private final IndirectProvider<InvitePresenter.Factory> invitePresenterFactoryProvider;

	private InvitePopupPresenter.Factory invitePopupPresenterFactory;

	private DispatchAsync dispatchAsync;

	@Inject
	public AffiliatesPresenter(final EventBus eventBus, final MyView view, final MyProxy proxy,
			final Provider<OtherUserPresenter> otherUserPresenterProvider,
			final Provider<InvitePresenter.Factory> InvitePresenterFactoryProvider,
			final InvitePopupPresenter.Factory invitePopupPresenterFactory, final DispatchAsync dispatchAsync) {
		super(eventBus, view, proxy, ApplicationPresenter.SLOT_Affiliates);

		this.otherUserPresenterProvider = new StandardProvider<OtherUserPresenter>(otherUserPresenterProvider);
		this.invitePresenterFactoryProvider = new StandardProvider<InvitePresenter.Factory>(
				InvitePresenterFactoryProvider);
		this.invitePopupPresenterFactory = invitePopupPresenterFactory;

		this.dispatchAsync = dispatchAsync;

		getView().setUiHandlers(this);
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
		// TODO temporary solution to initiate periodical update of affiliates values
		// refresh();

		// TODO perhaps we also have to exclude invites from periodical refresh - most changes initiated from UI

	}

	private void refresh() {
		dispatchAsync.execute(new GetAffiliatesAction(), new MyServerAsyncCallbackImpl<GetAffiliatesResult>() {

			@Override
			public void onSuccess(GetAffiliatesResult result) {
				displayAffiliates(result.getAffiliates());

			}
		});

		dispatchAsync.execute(new GetInvitesAction(), new MyServerAsyncCallbackImpl<GetInvitesResult>() {

			@Override
			public void onSuccess(GetInvitesResult result) {
				displayInvites(result.getInvites());
			}
		});
	}

	protected void displayAffiliates(List<UserDto> affiliates) {
		for (UserDto affiliate : affiliates) {
			displayAffiliate(affiliate);
		}

	}

	private void displayAffiliate(final UserDto affiliate) {
		otherUserPresenterProvider.get(new MyClientAsyncCallbackImpl<OtherUserPresenter>() {

			@Override
			public void onSuccess(OtherUserPresenter presenter) {
				presenter.setAndDisplay(affiliate);
				addToSlot(SLOT_Affiliates, presenter);
			}
		});		

	}

	protected void displayInvites(List<Invite> invites) {
		for (Invite invite : invites)
			displayInvite(invite);

	}

	public void displayInvite(final Invite invite) {
		InvitePresenter presenter = idToPresenterMap.get(invite.getId());
		if (presenter != null) {
			presenter.setAndDisplay(invite);
		} else {
			invitePresenterFactoryProvider.get(new MyClientAsyncCallbackImpl<InvitePresenter.Factory>() {

				@Override
				public void onSuccess(InvitePresenter.Factory presenterFactory) {
					InvitePresenter presenter = presenterFactory.create(AffiliatesPresenter.this);
					presenter.setAndDisplay(invite);
					addToSlot(SLOT_Invites, presenter);
					// store map to be able remove this presenter by its id
					idToPresenterMap.put(invite.getId(), presenter);
				}

			});
		}
	}

	@Override
	public void onInviteButtonClick() {
		addToPopupSlot(invitePopupPresenterFactory.create(this));
	}

	public void removeInvite(Long inviteId) {
		removeFromSlot(SLOT_Invites, idToPresenterMap.get(inviteId));
		idToPresenterMap.remove(inviteId);

	}

}
