package com.akolchin.stmg.client.application;

import javax.inject.Singleton;

import com.akolchin.stmg.client.application.affiliates.AffiliatesModule;
import com.akolchin.stmg.client.application.loginpage.LoginModule;
import com.akolchin.stmg.client.application.portfolio.PortfolioModule;
import com.akolchin.stmg.client.application.ranking.RankingModule;
import com.akolchin.stmg.client.application.search.SearchModule;
import com.akolchin.stmg.client.application.user.UserModule;
import com.akolchin.stmg.client.application.widget.WidgetModule;
import com.akolchin.stmg.shared.dto.UserDto;
import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class ApplicationModule extends AbstractPresenterModule {
	@Override
	protected void configure() {

		bind(UserDto.class).in(Singleton.class);

		bindPresenter(ApplicationPresenter.class, ApplicationPresenter.MyView.class, ApplicationView.class,
				ApplicationPresenter.MyProxy.class);

		install(new LoginModule());
		install(new UserModule());
		install(new PortfolioModule());
		install(new SearchModule());
		install(new RankingModule());
		install(new AffiliatesModule());
		install(new WidgetModule());

	}
}
