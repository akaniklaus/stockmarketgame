package com.akolchin.stmg.client.application.widget.login;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class LoginModule extends AbstractPresenterModule {
	@Override
	protected void configure() {
		bindSingletonPresenterWidget(LoginPresenter.class, LoginPresenter.MyView.class, LoginView.class);
	}
}
