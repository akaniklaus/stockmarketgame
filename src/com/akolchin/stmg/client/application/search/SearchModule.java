package com.akolchin.stmg.client.application.search;

import javax.inject.Singleton;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class SearchModule extends AbstractPresenterModule {
	@Override
	protected void configure() {
		bindPresenter(SearchPresenter.class, SearchPresenter.MyView.class, SearchView.class,
				SearchPresenter.MyProxy.class);

		bind(TimerInterface.class).to(SearchTimer.class).in(Singleton.class);
	}
}
