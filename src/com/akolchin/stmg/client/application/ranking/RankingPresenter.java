package com.akolchin.stmg.client.application.ranking;

import java.util.List;

import javax.inject.Provider;

import com.akolchin.stmg.client.application.ApplicationPresenter;
import com.akolchin.stmg.client.application.widget.otheruser.OtherUserPresenter;
import com.akolchin.stmg.client.dispatch.MyClientAsyncCallbackImpl;
import com.akolchin.stmg.client.dispatch.MyServerAsyncCallbackImpl;
import com.akolchin.stmg.client.event.PortfolioChangedEvent;
import com.akolchin.stmg.client.event.PortfolioChangedEvent.PortfolioChangedHandler;
import com.akolchin.stmg.client.place.NameTokens;
import com.akolchin.stmg.shared.action.GetRanksAction;
import com.akolchin.stmg.shared.action.GetRanksResult;
import com.akolchin.stmg.shared.dto.UserDto;
import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.common.client.IndirectProvider;
import com.gwtplatform.common.client.StandardProvider;
import com.gwtplatform.dispatch.rpc.shared.DispatchAsync;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.ContentSlot;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.client.proxy.RevealContentHandler;

public class RankingPresenter extends Presenter<RankingPresenter.MyView, RankingPresenter.MyProxy> implements
		PortfolioChangedHandler {
	interface MyView extends View {
	}

	@ContentSlot
	public static final Type<RevealContentHandler<?>> SLOT_Ranking = new Type<RevealContentHandler<?>>();

	@NameToken(NameTokens.ranking)
	@ProxyCodeSplit
	public interface MyProxy extends ProxyPlace<RankingPresenter> {
	}

	final IndirectProvider<OtherUserPresenter> otherUserPresenterProvider;

	private DispatchAsync dispatchAsync;

	@Inject
	public RankingPresenter(final EventBus eventBus, final MyView view, final MyProxy proxy,
			final Provider<OtherUserPresenter> otherUserPresenterProvider, final DispatchAsync dispatchAsync) {
		super(eventBus, view, proxy, ApplicationPresenter.SLOT_Ranking);

		this.otherUserPresenterProvider = new StandardProvider<OtherUserPresenter>(otherUserPresenterProvider);

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
		// TODO temporary solution to initiate periodical update of ranks list
		// refresh();
	}

	private void refresh() {
		dispatchAsync.execute(new GetRanksAction(), new MyServerAsyncCallbackImpl<GetRanksResult>() {

			@Override
			public void onSuccess(GetRanksResult result) {
				displayRanks(result.getRankedUsers());

			}
		});
	}

	protected void displayRanks(List<UserDto> rankedUsers) {
		for (UserDto rankedUser : rankedUsers) {
			displayRank(rankedUser);
		}

	}

	private void displayRank(final UserDto rankedUser) {
		otherUserPresenterProvider.get(new MyClientAsyncCallbackImpl<OtherUserPresenter>() {

			@Override
			public void onSuccess(OtherUserPresenter presenter) {
				presenter.setAndDisplay(rankedUser);
				addToSlot(SLOT_Ranking, presenter);
			}
		});

	}

}
