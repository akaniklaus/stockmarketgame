package com.akolchin.stmg.client.application.widget.stock.trade;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class TradeModule extends AbstractPresenterModule {
	@Override
	protected void configure() {
		bindSingletonPresenterWidget(TradePresenter.class, TradePresenter.MyView.class,
				TradeView.class);
	}
}
