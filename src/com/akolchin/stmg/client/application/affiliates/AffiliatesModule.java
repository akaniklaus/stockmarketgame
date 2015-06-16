package com.akolchin.stmg.client.application.affiliates;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class AffiliatesModule extends AbstractPresenterModule {
	@Override
	protected void configure() {

		bindPresenter(AffiliatesPresenter.class, AffiliatesPresenter.MyView.class, AffiliatesView.class,
				AffiliatesPresenter.MyProxy.class);
	}
}
