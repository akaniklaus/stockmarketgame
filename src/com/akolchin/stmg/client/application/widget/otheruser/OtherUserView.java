package com.akolchin.stmg.client.application.widget.otheruser;

import com.google.gwt.dom.client.Element;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewImpl;

public class OtherUserView extends ViewImpl implements OtherUserPresenter.MyView {
	public interface Binder extends UiBinder<HTMLPanel, OtherUserView> {
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
	OtherUserView(Binder binder) {
		initWidget(binder.createAndBindUi(this));
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
