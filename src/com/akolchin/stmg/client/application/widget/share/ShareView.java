package com.akolchin.stmg.client.application.widget.share;

import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;

public class ShareView extends ViewWithUiHandlers<ShareUiHandlers> implements SharePresenter.MyView {
	public interface Binder extends UiBinder<HTMLPanel, ShareView> {
	}

	@UiField
	Element symbolElement;
	@UiField
	Element lastTradePriceElement;
	
	@UiField
	Element sharesElement;
	@UiField
	Element ratioElement;


	@UiField
	Button buyButton;

	@UiField
	Button sellButton;

	@UiField
	SimplePanel tradePanel;

	@Inject
	ShareView(Binder binder) {
		initWidget(binder.createAndBindUi(this));
	}

	@Override
	public void displaySymbol(String symbol) {
		symbolElement.setInnerText(symbol);
	}

	@Override
	public void displayLastTradePrice(Double lastTradePrice) {
		lastTradePriceElement.setInnerText("$" + lastTradePrice);
	}
	
	@Override
	public void displayShares(Double shares) {
		sharesElement.setInnerText("" + shares);		
	}

	@Override
	public void displayRatio(Double ratio) {
		ratioElement.setInnerText("" + ratio + "%");
	}

	@Override
	public void enableTradeButtons() {
		buyButton.setEnabled(true);
		sellButton.setEnabled(true);
	}

	@UiHandler("buyButton")
	public void onBuyButtonClick(ClickEvent event) {
		sellButton.setEnabled(false);
		getUiHandlers().onBuyButtonClick();
	}

	@UiHandler("sellButton")
	public void onSellButtonClick(ClickEvent event) {
		buyButton.setEnabled(false);
		getUiHandlers().onSellButtonClick();
	}

	@UiHandler("deleteButton")
	public void onDeleteButtonClick(ClickEvent event) {
		getUiHandlers().onDeleteButtonClick();
	}

	@Override
	public void setInSlot(Object slot, IsWidget content) {
		if (slot == SharePresenter.SLOT_Trade) {
			tradePanel.clear();
			if (content != null)
				tradePanel.setWidget(content);
		} else {
			super.setInSlot(slot, content);
		}
	}

	@Override
	public void removeFromSlot(Object slot, IsWidget content) {
		if (slot == SharePresenter.SLOT_Trade) {
			tradePanel.remove(content);
		} else {
			super.removeFromSlot(slot, content);
		}
	}



}
