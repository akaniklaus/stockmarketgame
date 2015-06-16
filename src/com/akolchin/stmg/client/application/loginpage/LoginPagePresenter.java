package com.akolchin.stmg.client.application.loginpage;

import javax.inject.Inject;

import com.akolchin.stmg.client.application.widget.login.LoginPresenter;
import com.akolchin.stmg.client.place.NameTokens;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;

public class LoginPagePresenter extends Presenter<LoginPagePresenter.MyView, LoginPagePresenter.MyProxy> {
	public interface MyView extends View {
	}

	public static final Object TYPE_LoginPresenter = new Object();

	private LoginPresenter loginPresenter;

	@ProxyStandard
	@NameToken(NameTokens.login)
	public interface MyProxy extends ProxyPlace<LoginPagePresenter> {
	}

	@Inject
	public LoginPagePresenter(final EventBus eventBus, final MyView view, final MyProxy proxy,
			final LoginPresenter loginPresenter) {
		super(eventBus, view, proxy, RevealType.Root); // ApplicationPresenter.TYPE_SetMainContent);

		this.loginPresenter = loginPresenter;
	}

	@Override
	protected void onBind() {
		super.onBind();

		setInSlot(TYPE_LoginPresenter, loginPresenter);
	}
}
