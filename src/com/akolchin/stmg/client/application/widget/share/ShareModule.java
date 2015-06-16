package com.akolchin.stmg.client.application.widget.share;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class ShareModule extends AbstractPresenterModule {
	@Override
	protected void configure() {
		bindPresenterWidget(SharePresenter.class, SharePresenter.MyView.class, ShareView.class);
	}
}
