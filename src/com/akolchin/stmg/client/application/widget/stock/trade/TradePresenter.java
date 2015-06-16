package com.akolchin.stmg.client.application.widget.stock.trade;

import com.akolchin.stmg.client.application.widget.share.SharePresenter;
import com.akolchin.stmg.client.dispatch.MyServerAsyncCallbackImpl;
import com.akolchin.stmg.client.event.PortfolioChangedEvent;
import com.akolchin.stmg.shared.TradeAction;
import com.akolchin.stmg.shared.action.DoTradeAction;
import com.akolchin.stmg.shared.action.DoTradeResult;
import com.google.gwt.user.client.Window;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.dispatch.rpc.shared.DispatchAsync;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;

public class TradePresenter extends PresenterWidget<TradePresenter.MyView> implements TradeUiHandlers {
	public interface MyView extends View, HasUiHandlers<TradeUiHandlers> {

		void displayTradeAmount(Double tradeAmount);
	}

	private DispatchAsync dispatchAsync;

	private TradeAction tradeAction;
	private String symbol;
	private SharePresenter parentPresenter;

	@Inject
	TradePresenter(EventBus eventBus, MyView view, DispatchAsync dispatchAsync) {
		super(eventBus, view);

		this.dispatchAsync = dispatchAsync;

		getView().setUiHandlers(this);
	}

	@Override
	public void onTradeButtonClick(Double tradeAmount) {

		dispatchAsync.execute(new DoTradeAction(tradeAction, symbol, tradeAmount),
				new MyServerAsyncCallbackImpl<DoTradeResult>() {

					@Override
					public void onSuccess(DoTradeResult result) {
						if (result.getIsSuccess()) {
							fireEvent(new PortfolioChangedEvent());
							// parentPresenter.refresh();
							removeFromParent();
						} else
							Window.alert("Action was not succeed on the server for some reasons");
					}
				});
	}

	@Override
	public void onCloseButtonClick() {
		removeFromParent();
	}

	public TradePresenter init(TradeAction tradeAction, String symbol, SharePresenter parentPresenter) {
		// TODO probably such better to do in one of life-cycle methods
		if (this.parentPresenter != null)
			removeFromParent();

		this.parentPresenter = parentPresenter;

		this.tradeAction = tradeAction;
		this.symbol = symbol;

		getView().displayTradeAmount(0d);

		return this;
	}

	private void removeFromParent() {
		parentPresenter.removeTradeWidget(this);
	}

	public void deinit() {
		tradeAction = null;
		symbol = null;
		parentPresenter = null; // most important
	}

}
