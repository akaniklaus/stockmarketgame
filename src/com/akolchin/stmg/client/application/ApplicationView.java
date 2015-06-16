package com.akolchin.stmg.client.application;

import javax.inject.Inject;

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewImpl;

public class ApplicationView extends ViewImpl implements ApplicationPresenter.MyView {
	interface Binder extends UiBinder<Widget, ApplicationView> {
	}

	@UiField
	Panel main;

	@UiField
	SimplePanel user;

	@UiField
	SimplePanel search;

	@UiField
	SimplePanel ranking;

	@UiField
	SimplePanel portfolio;

	@UiField
	SimplePanel affiliates;

	@Inject
	ApplicationView(Binder uiBinder) {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public void setInSlot(Object slot, IsWidget content) {
		if (slot == ApplicationPresenter.SLOT_User) {
			user.add(content);
		} else if (slot == ApplicationPresenter.SLOT_Search) {
			search.add(content);
		} else if (slot == ApplicationPresenter.SLOT_Ranking) {
			ranking.add(content);
		} else if (slot == ApplicationPresenter.SLOT_Portfolio) {
			portfolio.add(content);
		} else if (slot == ApplicationPresenter.SLOT_Affiliates) {
			affiliates.add(content);
		} else {
			super.setInSlot(slot, content);
		}
	}
}
