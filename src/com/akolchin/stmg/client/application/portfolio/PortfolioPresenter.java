package com.akolchin.stmg.client.application.portfolio;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Provider;

import com.akolchin.stmg.client.application.ApplicationPresenter;
import com.akolchin.stmg.client.application.widget.share.SharePresenter;
import com.akolchin.stmg.client.dispatch.MyClientAsyncCallbackImpl;
import com.akolchin.stmg.client.dispatch.MyServerAsyncCallbackImpl;
import com.akolchin.stmg.client.event.PortfolioChangedEvent;
import com.akolchin.stmg.client.event.PortfolioChangedEvent.PortfolioChangedHandler;
import com.akolchin.stmg.client.event.ShareAddedToPortfolioEvent;
import com.akolchin.stmg.client.event.ShareAddedToPortfolioEvent.ShareAddedToPortfolioHandler;
import com.akolchin.stmg.client.event.ShareDeletedFromPortfolioEvent;
import com.akolchin.stmg.client.event.ShareDeletedFromPortfolioEvent.ShareDeletedFromPortfolioHandler;
import com.akolchin.stmg.client.place.NameTokens;
import com.akolchin.stmg.shared.action.GetPortfolioAction;
import com.akolchin.stmg.shared.action.GetPortfolioResult;
import com.akolchin.stmg.shared.domain.Share;
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

public class PortfolioPresenter extends Presenter<PortfolioPresenter.MyView, PortfolioPresenter.MyProxy> implements
		ShareAddedToPortfolioHandler, ShareDeletedFromPortfolioHandler, PortfolioChangedHandler {
	interface MyView extends View {
	}

	@ContentSlot
	public static final Type<RevealContentHandler<?>> SLOT_Portfolio = new Type<RevealContentHandler<?>>();

	private final Map<Long, SharePresenter> idToPresenterMap = new HashMap<Long, SharePresenter>();

	@NameToken(NameTokens.portfolio)
	@ProxyCodeSplit
	public interface MyProxy extends ProxyPlace<PortfolioPresenter> {
	}

	private final IndirectProvider<SharePresenter> sharePresenterProvider;

	private DispatchAsync dispatchAsync;

	@Inject
	public PortfolioPresenter(final EventBus eventBus, final MyView view, final MyProxy proxy,
			final Provider<SharePresenter> sharePresenterProvider, final DispatchAsync dispatchAsync) {
		super(eventBus, view, proxy, ApplicationPresenter.SLOT_Portfolio);

		this.sharePresenterProvider = new StandardProvider<SharePresenter>(sharePresenterProvider);

		this.dispatchAsync = dispatchAsync;

	}

	@Override
	protected void onBind() {
		super.onBind();

		addHandler(ShareAddedToPortfolioEvent.getType(), this);
		addHandler(ShareDeletedFromPortfolioEvent.getType(), this);
		addHandler(PortfolioChangedEvent.getType(), this);
	}

	@Override
	protected void onReset() {
		super.onReset();

		refresh();
	}

	@Override
	public void onShareAddedToPortfolio(ShareAddedToPortfolioEvent event) {
		displayShare(event.getShare());
	}

	@Override
	public void onShareDeletedFromPortfolio(ShareDeletedFromPortfolioEvent event) {
		Long id = event.getShare().getId();
		removeFromSlot(SLOT_Portfolio, idToPresenterMap.get(id));
		idToPresenterMap.remove(id);
	}

	@Override
	public void onPortfolioChanged(PortfolioChangedEvent event) {
		refresh();
	}

	private void refresh() {
		dispatchAsync.execute(new GetPortfolioAction(), new MyServerAsyncCallbackImpl<GetPortfolioResult>() {

			@Override
			public void onSuccess(GetPortfolioResult result) {
				displayShares(result.getShares());
			}
		});
	}

	private void displayShares(List<Share> shares) {
		Double portfolioValue = 0d;
		for (final Share share : shares)
			portfolioValue += share.getShares() * share.getStock().getLastTradePrice();

		for (final Share share : shares)
			displayShare(share, portfolioValue);

	}

	private void displayShare(Share share) {
		displayShare(share, null);
	}

	private void displayShare(final Share share, final Double portfolioValue) {
		SharePresenter presenter = idToPresenterMap.get(share.getId());
		if (presenter != null) {
			presenter.setAndDisplay(share, portfolioValue);
		} else {

			sharePresenterProvider.get(new MyClientAsyncCallbackImpl<SharePresenter>() {

				@Override
				public void onSuccess(SharePresenter presenter) {
					presenter.setAndDisplay(share, portfolioValue);
					addToSlot(SLOT_Portfolio, presenter);
					// store map to be able remove this presenter by its id
					idToPresenterMap.put(share.getId(), presenter);
				}

			});
		}
	}
}
