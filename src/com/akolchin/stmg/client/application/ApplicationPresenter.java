package com.akolchin.stmg.client.application;

import com.akolchin.stmg.client.application.affiliates.AffiliatesPresenter;
import com.akolchin.stmg.client.application.portfolio.PortfolioPresenter;
import com.akolchin.stmg.client.application.ranking.RankingPresenter;
import com.akolchin.stmg.client.application.search.SearchPresenter;
import com.akolchin.stmg.client.application.user.UserPresenter;
import com.akolchin.stmg.client.place.NameTokens;
import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.ContentSlot;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.client.proxy.RevealContentHandler;

public class ApplicationPresenter extends Presenter<ApplicationPresenter.MyView, ApplicationPresenter.MyProxy> {
	interface MyView extends View {
	}

	@ContentSlot
	public static final Type<RevealContentHandler<?>> SLOT_User = new Type<RevealContentHandler<?>>();
	@ContentSlot
	public static final Type<RevealContentHandler<?>> SLOT_Search = new Type<RevealContentHandler<?>>();
	@ContentSlot
	public static final Type<RevealContentHandler<?>> SLOT_Portfolio = new Type<RevealContentHandler<?>>();
	@ContentSlot
	public static final Type<RevealContentHandler<?>> SLOT_Ranking = new Type<RevealContentHandler<?>>();
	@ContentSlot
	public static final Type<RevealContentHandler<?>> SLOT_Affiliates = new Type<RevealContentHandler<?>>();

	private final EventBus eventBus;
	private final UserPresenter userPresenter;
	private final SearchPresenter searchPresenter;
	private final RankingPresenter rankingPresenter;
	private final PortfolioPresenter portfolioPresenter;
	private final AffiliatesPresenter affiliatesPresenter;

	@NameToken(NameTokens.application)
	@ProxyCodeSplit
	public interface MyProxy extends ProxyPlace<ApplicationPresenter> {
	}

	@Inject
	public ApplicationPresenter(EventBus eventBus, MyView view, MyProxy proxy, UserPresenter userPresenter,
			SearchPresenter searchPresenter, RankingPresenter rankingPresenter, PortfolioPresenter portfolioPresenter,
			AffiliatesPresenter affiliatesPresenter) {
		super(eventBus, view, proxy, RevealType.RootLayout);

		this.eventBus = eventBus;
		this.userPresenter = userPresenter;
		this.searchPresenter = searchPresenter;
		this.rankingPresenter = rankingPresenter;
		this.portfolioPresenter = portfolioPresenter;
		this.affiliatesPresenter = affiliatesPresenter;
	}

	@Override
	protected void onBind() {
		super.onBind();

		// TODO why it is not enough just to create these presenter?

		setInSlot(SLOT_User, userPresenter);
		setInSlot(SLOT_Search, searchPresenter);
		setInSlot(SLOT_Ranking, rankingPresenter);
		setInSlot(SLOT_Portfolio, portfolioPresenter);
		setInSlot(SLOT_Affiliates, affiliatesPresenter);
	}

}
