package com.akolchin.stmg.client.application.widget.stock;

import javax.inject.Inject;

import com.akolchin.stmg.client.dispatch.MyServerAsyncCallbackImpl;
import com.akolchin.stmg.client.event.ShareAddedToPortfolioEvent;
import com.akolchin.stmg.shared.action.AddStockToPortfolioAction;
import com.akolchin.stmg.shared.action.AddStockToPortfolioResult;
import com.akolchin.stmg.shared.domain.Share;
import com.akolchin.stmg.shared.domain.Stock;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.dispatch.rpc.shared.DispatchAsync;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;

public class StockPresenter extends PresenterWidget<StockPresenter.MyView> implements StockUiHandlers {
	public interface MyView extends View, HasUiHandlers<StockUiHandlers> {

		void displaySymbol(String s);

		void displayName(String s);

		void displayLastTradeDateTime(Long long1);

		void displayLastTradePrice(Double double1);

		void displayPriceChange(Double double1);

		void displayPriceChangeInPercent(Double double1);
	}

	private DispatchAsync dispatchAsync;

	@Inject
	StockPresenter(EventBus eventBus, MyView view, DispatchAsync dispatchAsync) {
		super(eventBus, view);

		this.dispatchAsync = dispatchAsync;

		getView().setUiHandlers(this);
	}

	Stock stock;

	public void setAndDisplay(Stock stock) {
		this.stock = stock;

		getView().displaySymbol(stock.getSymbol());
		getView().displayName(stock.getName());
		getView().displayLastTradePrice(stock.getLastTradePrice());
		getView().displayPriceChange(stock.getPriceChange());
		getView().displayPriceChangeInPercent(stock.getPriceChangeInPercent());
	}

	public void onAddButtonClick() {
		// call server to add stock
		dispatchAsync.execute(new AddStockToPortfolioAction(stock.getSymbol()),
				new MyServerAsyncCallbackImpl<AddStockToPortfolioResult>() {

					@Override
					public void onSuccess(AddStockToPortfolioResult result) {

						Share share = result.getShare();
						if (share != null)
							fireEvent(new ShareAddedToPortfolioEvent(share));

					}
				});

	}
}