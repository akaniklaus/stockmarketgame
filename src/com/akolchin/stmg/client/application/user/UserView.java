package com.akolchin.stmg.client.application.user;

import javax.inject.Inject;

import com.google.gwt.dom.client.Element;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewImpl;

public class UserView extends ViewImpl implements UserPresenter.MyView {
	interface Binder extends UiBinder<Widget, UserView> {
	}

	@UiField
	Element nameElement;
	@UiField
	Element cashElement;
	@UiField
	Element valueElement;
	@UiField
	Element returnPctElement;

	@Inject
	UserView(Binder uiBinder) {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public void displayName(String name) {
		nameElement.setInnerText(name);
	}

	@Override
	public void displayCash(Double cash) {
		cashElement.setInnerText("$" + cash);
	}

	@Override
	public void displayValue(Double value) {
		valueElement.setInnerText("$" + value);		
	}

	@Override
	public void displayReturnPct(Double returnPct) {
		returnPctElement.setInnerText("" + returnPct + "%");			
	}
}
