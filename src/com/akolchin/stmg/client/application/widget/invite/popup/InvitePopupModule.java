package com.akolchin.stmg.client.application.widget.invite.popup;

import com.google.gwt.inject.client.assistedinject.GinFactoryModuleBuilder;
import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class InvitePopupModule extends AbstractPresenterModule {
	@Override
	protected void configure() {
		// bindSingletonPresenterWidget(InvitePopupPresenter.class, InvitePopupPresenter.MyView.class,
		// InvitePopupView.class);

		// TODO try to bind and install as singleton as it was initially
		bind(InvitePopupPresenter.MyView.class).to(InvitePopupView.class);
		install(new GinFactoryModuleBuilder().build(InvitePopupPresenter.Factory.class));
	}
}
