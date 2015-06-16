package com.akolchin.stmg.client.application.ranking;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class RankingModule extends AbstractPresenterModule {
	@Override
	protected void configure() {
		bindPresenter(RankingPresenter.class, RankingPresenter.MyView.class, RankingView.class,
				RankingPresenter.MyProxy.class);
	}
}
