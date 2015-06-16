package com.akolchin.stmg.client.application.affiliates;

import javax.inject.Inject;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;

public class AffiliatesView extends ViewWithUiHandlers<AffiliatesUiHandlers> implements AffiliatesPresenter.MyView {
	interface Binder extends UiBinder<Widget, AffiliatesView> {
	}

	@UiField
	Panel invitesPanel;
	@UiField
	Panel affiliatesPanel;

	@Inject
	AffiliatesView(Binder uiBinder) {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiHandler("inviteButton")
	public void onInviteButtonClick(ClickEvent event) {
		getUiHandlers().onInviteButtonClick();
	}

	@Override
	public void setInSlot(Object slot, IsWidget content) {
		if (slot == AffiliatesPresenter.SLOT_Invites) {
			invitesPanel.clear();
			invitesPanel.add(content);
		} else if (slot == AffiliatesPresenter.SLOT_Affiliates) {
			affiliatesPanel.clear();
			affiliatesPanel.add(content);
		} else
			super.setInSlot(slot, content);

	}

	@Override
	public void addToSlot(Object slot, IsWidget content) {
		if (slot == AffiliatesPresenter.SLOT_Invites)
			invitesPanel.add(content);
		else if (slot == AffiliatesPresenter.SLOT_Affiliates)
			affiliatesPanel.add(content);
		else
			super.addToSlot(slot, content);
	}

	@Override
	public void removeFromSlot(Object slot, IsWidget content) {
		if (slot == AffiliatesPresenter.SLOT_Invites)
			invitesPanel.remove(content);
		else if (slot == AffiliatesPresenter.SLOT_Affiliates)
			affiliatesPanel.remove(content);
		else
			super.removeFromSlot(slot, content);
	}

}
