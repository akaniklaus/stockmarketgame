package com.akolchin.stmg.client.application.portfolio;

import javax.inject.Inject;

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewImpl;

public class PortfolioView extends ViewImpl implements PortfolioPresenter.MyView {
	interface Binder extends UiBinder<Widget, PortfolioView> {
	}

	@UiField
	Panel main;

	@Inject
	PortfolioView(Binder uiBinder) {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public void setInSlot(Object slot, IsWidget content) {
		if (slot == PortfolioPresenter.SLOT_Portfolio) {
			main.clear();
			main.add(content);
		} else {
			super.setInSlot(slot, content);
		}
	}

	@Override
	public void addToSlot(Object slot, IsWidget content) {
		if (slot == PortfolioPresenter.SLOT_Portfolio)
			main.add(content);
		else
			super.addToSlot(slot, content);
	}

	@Override
	public void removeFromSlot(Object slot, IsWidget content) {
		if (slot == PortfolioPresenter.SLOT_Portfolio) {
			main.remove(content);
		} else
			super.removeFromSlot(slot, content);
	}
}
