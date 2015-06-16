package com.akolchin.stmg.client.application.user;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class UserModule extends AbstractPresenterModule {
	@Override
	protected void configure() {
		bindPresenter(UserPresenter.class, UserPresenter.MyView.class, UserView.class, UserPresenter.MyProxy.class);
	}
}
