package com.akolchin.stmg.client.application.search;

import java.util.List;

import javax.inject.Provider;

import com.akolchin.stmg.client.application.ApplicationPresenter;
import com.akolchin.stmg.client.application.widget.stock.StockPresenter;
import com.akolchin.stmg.client.dispatch.MyClientAsyncCallbackImpl;
import com.akolchin.stmg.client.dispatch.MyServerAsyncCallbackImpl;
import com.akolchin.stmg.client.event.PortfolioChangedEvent;
import com.akolchin.stmg.client.place.NameTokens;
import com.akolchin.stmg.shared.action.GetStocksAction;
import com.akolchin.stmg.shared.action.GetStocksResult;
import com.akolchin.stmg.shared.domain.Stock;
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

public class SearchPresenter extends Presenter<SearchPresenter.MyView, SearchPresenter.MyProxy> {
	interface MyView extends View {

		void clear();
	}

	@ContentSlot
	public static final Type<RevealContentHandler<?>> SLOT_Search = new Type<RevealContentHandler<?>>();

	@NameToken(NameTokens.search)
	@ProxyCodeSplit
	public interface MyProxy extends ProxyPlace<SearchPresenter> {
	}

	private final TimerInterface timer;

	private final IndirectProvider<StockPresenter> stockPresenterProvider;

	private DispatchAsync dispatchAsync;

	@Inject
	public SearchPresenter(EventBus eventBus, MyView view, MyProxy proxy, TimerInterface timer,
			Provider<StockPresenter> stockPresenterProvider, DispatchAsync dispatchAsync) {
		super(eventBus, view, proxy, ApplicationPresenter.SLOT_Search);

		this.timer = timer;
		this.timer.setPresenter(this);

		this.stockPresenterProvider = new StandardProvider<StockPresenter>(stockPresenterProvider);

		this.dispatchAsync = dispatchAsync;

	}

	@Override
	protected void onReset() {
		super.onReset();

		refresh();
	}

	private void displayStocks(List<Stock> list) {

		getView().clear();

		for (final Stock stock : list) {
			stockPresenterProvider.get(new MyClientAsyncCallbackImpl<StockPresenter>() {

				@Override
				public void onSuccess(StockPresenter presenter) {
					presenter.setAndDisplay(stock);
					addToSlot(SLOT_Search, presenter);
				}
			});

		}

	}

	public void refresh() {
		// called by timer
		timer.stop();

		dispatchAsync.execute(new GetStocksAction(), new MyServerAsyncCallbackImpl<GetStocksResult>() {

			@Override
			public void onSuccess(GetStocksResult result) {
				displayStocks(result.getStocks());

				// TODO temporary solution to refresh user values
				fireEvent(new PortfolioChangedEvent());

				timer.start();

			}

		});
	}
}
