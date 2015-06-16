package com.akolchin.stmg.client.application.widget.share;

import com.akolchin.stmg.client.application.widget.stock.trade.TradePresenter;
import com.akolchin.stmg.client.dispatch.MyServerAsyncCallbackImpl;
import com.akolchin.stmg.client.event.ShareDeletedFromPortfolioEvent;
import com.akolchin.stmg.shared.TradeAction;
import com.akolchin.stmg.shared.action.DeleteShareFromPortfolioAction;
import com.akolchin.stmg.shared.action.DeleteShareFromPortfolioResult;
import com.akolchin.stmg.shared.action.GetShareAction;
import com.akolchin.stmg.shared.action.GetShareResult;
import com.akolchin.stmg.shared.domain.Share;
import com.akolchin.stmg.shared.dto.UserDto;
import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.dispatch.rpc.shared.DispatchAsync;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.ContentSlot;
import com.gwtplatform.mvp.client.proxy.RevealContentHandler;

public class SharePresenter extends PresenterWidget<SharePresenter.MyView> implements ShareUiHandlers {
	public interface MyView extends View, HasUiHandlers<ShareUiHandlers> {

		void displaySymbol(String symbol);

		void displayLastTradePrice(Double lastTradePrice);

		void enableTradeButtons();

		void displayShares(Double shares);

		void displayRatio(Double ratio);
	}

	@ContentSlot
	public static final Type<RevealContentHandler<?>> SLOT_Trade = new Type<RevealContentHandler<?>>();

	private DispatchAsync dispatchAsync;
	private TradePresenter tradePresenter;

	private final UserDto currentUser;

	private Share share;

	private boolean isInTrade;

	@Inject
	SharePresenter(EventBus eventBus, MyView view, DispatchAsync dispatchAsync, TradePresenter tradePresenter,
			UserDto currentUser) {
		super(eventBus, view);

		this.dispatchAsync = dispatchAsync;

		this.tradePresenter = tradePresenter;

		this.currentUser = currentUser;

		getView().setUiHandlers(this);
	}

	@Override
	public void onBuyButtonClick() {
		onOneOfTradeButtonsClick(TradeAction.BUY);
	}

	@Override
	public void onSellButtonClick() {
		onOneOfTradeButtonsClick(TradeAction.SELL);
	}

	@Override
	public void onDeleteButtonClick() {
		// call server to add stock
		dispatchAsync.execute(new DeleteShareFromPortfolioAction(share.getSymbol()),
				new MyServerAsyncCallbackImpl<DeleteShareFromPortfolioResult>() {

					@Override
					public void onSuccess(DeleteShareFromPortfolioResult result) {

						if (result.getIsDeleted())
							getEventBus().fireEvent(new ShareDeletedFromPortfolioEvent(share));

					}
				});

	}

	private void onOneOfTradeButtonsClick(TradeAction tradeAction) {
		if (!isInTrade) {
			setInSlot(SLOT_Trade, tradePresenter.init(tradeAction, share.getSymbol(), this), false);
			isInTrade = true;
		} else
			removeTradeWidget(tradePresenter);
	}

	public void removeTradeWidget(TradePresenter tradePresenter) {
		removeFromSlot(SLOT_Trade, tradePresenter);
		tradePresenter.deinit();
		isInTrade = false;
		getView().enableTradeButtons();
	}

	public void refresh() {
		dispatchAsync.execute(new GetShareAction(share.getSymbol()), new MyServerAsyncCallbackImpl<GetShareResult>() {
			@Override
			public void onSuccess(GetShareResult result) {
				// currentUser.getValue() should be already updated at this moment
				setAndDisplay(result.getShare(), null);
			}
		});
	}

	public void setAndDisplay(Share share, Double portfolioValue) {
		this.share = share;

		if (portfolioValue == null) // since value not provided try to get it from currentUser
			// but currentUser.getValue() should be already updated at this moment
			portfolioValue = currentUser.getValue();

		Double lastPrice = share.getStock().getLastTradePrice();
		Double shares = share.getShares();

		getView().displaySymbol(share.getSymbol());
		getView().displayLastTradePrice(lastPrice);
		getView().displayShares(shares);
		getView().displayRatio(portfolioValue != 0 ? ((shares * lastPrice) / portfolioValue) * 100 : 0);

	}

}
