package com.akolchin.stmg.client.application.widget.login;

import javax.inject.Inject;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Panel;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;

public class LoginView extends ViewWithUiHandlers<LoginUiHandlers> implements LoginPresenter.MyView {
	public interface Binder extends UiBinder<HTMLPanel, LoginView> {
	}

	@UiField
	Panel widgetPanel;

	@Inject
	public LoginView(final Binder binder) {
		initWidget(binder.createAndBindUi(this));

	}

	@UiHandler("signinWithFB")
	public void onSigninWithFB(ClickEvent event) {
		getUiHandlers().onSigninWithFB();
	}

}
