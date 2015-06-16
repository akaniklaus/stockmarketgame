package com.akolchin.stmg.client.application.widget.stock.trade;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.DoubleBox;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;

public class TradeView extends ViewWithUiHandlers<TradeUiHandlers> implements TradePresenter.MyView {
	public interface Binder extends UiBinder<HTMLPanel, TradeView> {
	}

	@UiField
	HTMLPanel panel;

	@UiField
	DoubleBox tradeAmountBox;

	@Inject
	TradeView(Binder binder) {
		initWidget(binder.createAndBindUi(this));
	}

	@UiHandler("tradeButton")
	public void onTradeButtonClick(ClickEvent event) {
		getUiHandlers().onTradeButtonClick(tradeAmountBox.getValue());
	}

	@UiHandler("closeButton")
	public void onCloseButtonClick(ClickEvent event) {
		getUiHandlers().onCloseButtonClick();
	}

	@Override
	public void displayTradeAmount(Double tradeAmount) {
		tradeAmountBox.setValue(tradeAmount);
	}

}
