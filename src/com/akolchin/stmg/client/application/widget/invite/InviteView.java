package com.akolchin.stmg.client.application.widget.invite;

import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;

public class InviteView extends ViewWithUiHandlers<InviteUiHandlers> implements InvitePresenter.MyView {
	public interface Binder extends UiBinder<HTMLPanel, InviteView> {
	}

	@UiField
	HTMLPanel panel;

	@UiField
	Element nameElement;

	@Inject
	InviteView(Binder binder) {
		initWidget(binder.createAndBindUi(this));
	}

	@Override
	public void displayName(String name) {
		nameElement.setInnerText(name);
	}
	
	@UiHandler("deleteButton")
	public void onDeleteButtonClick(ClickEvent event) {
		getUiHandlers().onDeleteButtonClick();
	}
}
