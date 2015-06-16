package com.akolchin.stmg.client.application.ranking;

import javax.inject.Inject;

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewImpl;

public class RankingView extends ViewImpl implements RankingPresenter.MyView {
	interface Binder extends UiBinder<Widget, RankingView> {
	}

	@UiField
	Panel main;

	@Inject
	RankingView(Binder uiBinder) {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public void setInSlot(Object slot, IsWidget content) {
		if (slot == RankingPresenter.SLOT_Ranking) {
			main.clear();
			main.add(content);
		} else {
			super.setInSlot(slot, content);
		}
	}

	@Override
	public void addToSlot(Object slot, IsWidget content) {
		if (slot == RankingPresenter.SLOT_Ranking)
			main.add(content);
		else
			super.addToSlot(slot, content);
	}

}
