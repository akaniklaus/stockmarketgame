package com.akolchin.stmg.client.gin;

import com.akolchin.stmg.client.application.ApplicationModule;
import com.akolchin.stmg.client.place.NameTokens;
import com.gwtplatform.dispatch.rpc.client.gin.RpcDispatchAsyncModule;
import com.gwtplatform.mvp.client.annotations.DefaultPlace;
import com.gwtplatform.mvp.client.annotations.ErrorPlace;
import com.gwtplatform.mvp.client.annotations.UnauthorizedPlace;
import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;
import com.gwtplatform.mvp.client.gin.DefaultModule;
import com.gwtplatform.mvp.client.proxy.DefaultPlaceManager;

/**
 * See more on setting up the PlaceManager on <a
 * href="// See more on: https://github.com/ArcBees/GWTP/wiki/PlaceManager" >DefaultModule's > DefaultPlaceManager</a>
 */
public class ClientModule extends AbstractPresenterModule {
	@Override
	protected void configure() {

		install(new DefaultModule(DefaultPlaceManager.class));
		install(new RpcDispatchAsyncModule.Builder().build());
		install(new ApplicationModule());

		// DefaultPlaceManager Places
		bindConstant().annotatedWith(DefaultPlace.class).to(NameTokens.login);

		// TODO map to dedicated tokens
		bindConstant().annotatedWith(ErrorPlace.class).to(NameTokens.getApplication());
		bindConstant().annotatedWith(UnauthorizedPlace.class).to(NameTokens.login);

	}

}
