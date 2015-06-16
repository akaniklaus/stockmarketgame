package com.akolchin.stmg.client.application.widget.stock;

import java.util.Date;

import javax.inject.Inject;

import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;

public class StockView extends ViewWithUiHandlers<StockUiHandlers> implements StockPresenter.MyView {
	public interface Binder extends UiBinder<HTMLPanel, StockView> {
	}

	@UiField
	HTMLPanel panel;
	@UiField
	Element symbolElement;
	@UiField
	Element nameElement;
	@UiField
	Element lastTradeDateTimeElement;
	@UiField
	Element lastTradePriceElement;
	@UiField
	Element priceChangeElement;
	@UiField
	Element priceChangeInPercentElement;

	@Inject
	StockView(Binder binder) {
		initWidget(binder.createAndBindUi(this));
	}

	@Override
	public void displaySymbol(String symbol) {
		symbolElement.setInnerText(symbol);
	}

	@Override
	public void displayName(String name) {
		nameElement.setInnerText(name);
	}

	@Override
	public void displayLastTradeDateTime(Long lastTradeDateTimeAsMillis) {
		lastTradeDateTimeElement.setInnerText(lastTradeDateTimeAsMillis == null ? null : (new Date(
				lastTradeDateTimeAsMillis.longValue())).toString());
	}

	@Override
	public void displayLastTradePrice(Double lastTradePrice) {
		lastTradePriceElement.setInnerText(lastTradePrice.toString());
	}

	@Override
	public void displayPriceChange(Double priceChange) {
		priceChangeElement.setInnerText(priceChange.toString());
	}

	@Override
	public void displayPriceChangeInPercent(Double priceChangeInPercent) {
		priceChangeInPercentElement.setInnerText((new StringBuilder(String.valueOf(priceChangeInPercent.toString())))
				.append("%").toString());
	}

	@UiHandler("addButton")
	public void onAddButtonClick(ClickEvent event) {
		getUiHandlers().onAddButtonClick();
	}

}