package com.akolchin.stmg.client.application.widget.invite;

import com.google.gwt.inject.client.assistedinject.GinFactoryModuleBuilder;
import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class InviteModule extends AbstractPresenterModule {
	@Override
	protected void configure() {
		// bindPresenterWidget(InvitePresenter.class, InvitePresenter.MyView.class, InviteView.class);
		// bindPresenterWidgetFactory(presenterFactory, presenterFactoryImpl, viewFactory, viewFactoryImpl);

		bind(InvitePresenter.MyView.class).to(InviteView.class);
		install(new GinFactoryModuleBuilder().build(InvitePresenter.Factory.class));
	}
}
