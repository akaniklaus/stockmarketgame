package com.akolchin.stmg.client.application.widget.otheruser;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class OtherUserModule extends AbstractPresenterModule {
	@Override
	protected void configure() {
		bindPresenterWidget(OtherUserPresenter.class, OtherUserPresenter.MyView.class, OtherUserView.class);
	}
}
