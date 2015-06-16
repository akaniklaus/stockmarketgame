package com.akolchin.stmg.client.application.widget.stock.trade;

import com.gwtplatform.mvp.client.UiHandlers;

public interface TradeUiHandlers extends UiHandlers {

	void onTradeButtonClick(Double tradeAmount);

	void onCloseButtonClick();
}
