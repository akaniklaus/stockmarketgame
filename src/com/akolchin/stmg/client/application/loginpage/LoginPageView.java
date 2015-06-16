package com.akolchin.stmg.client.application.loginpage;

import javax.inject.Inject;

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewImpl;

public class LoginPageView extends ViewImpl implements LoginPagePresenter.MyView {
	public interface Binder extends UiBinder<Widget, LoginPageView> {
	}

	@UiField
	Panel pagePanel;
	@UiField
	SimplePanel loginPanel;

	@Inject
	public LoginPageView(final Binder uiBinder) {
		initWidget(uiBinder.createAndBindUi(this));

		pagePanel.getElement().setId("login_page");
	}

	@Override
	public void setInSlot(Object slot, IsWidget content) {
		if (slot == LoginPagePresenter.TYPE_LoginPresenter) {
			loginPanel.setWidget(content);
		} else {
			super.setInSlot(slot, content);
		}
	}
}
