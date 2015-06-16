package com.akolchin.stmg.client.application.search;

import javax.inject.Inject;

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewImpl;

public class SearchView extends ViewImpl implements SearchPresenter.MyView {
	interface Binder extends UiBinder<Widget, SearchView> {
	}

	@UiField
	Panel main;

	@Inject
	SearchView(Binder uiBinder) {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public void setInSlot(Object slot, IsWidget content) {
		if (slot == SearchPresenter.SLOT_Search) {
			main.clear();
			main.add(content);
		} else {
			super.setInSlot(slot, content);
		}
	}

	@Override
	public void addToSlot(Object slot, IsWidget content) {
		if (slot == SearchPresenter.SLOT_Search) {
			main.add(content);
		} else
			super.addToSlot(slot, content);
	}

	@Override
	public void clear() {
		main.clear();
	}
}
