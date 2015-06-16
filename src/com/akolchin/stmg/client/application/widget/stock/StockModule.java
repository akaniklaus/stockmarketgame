package com.akolchin.stmg.client.application.widget.stock;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class StockModule extends AbstractPresenterModule {
	@Override
	protected void configure() {
		bindPresenterWidget(StockPresenter.class, StockPresenter.MyView.class, StockView.class);
	}
}