package com.akolchin.stmg.client.application.portfolio;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class PortfolioModule extends AbstractPresenterModule {
	@Override
	protected void configure() {
		bindPresenter(PortfolioPresenter.class, PortfolioPresenter.MyView.class, PortfolioView.class,
				PortfolioPresenter.MyProxy.class);
	}
}
