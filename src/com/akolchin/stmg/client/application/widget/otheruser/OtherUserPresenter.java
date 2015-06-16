package com.akolchin.stmg.client.application.widget.otheruser;

import com.akolchin.stmg.shared.dto.UserDto;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;

public class OtherUserPresenter extends PresenterWidget<OtherUserPresenter.MyView> {
	public interface MyView extends View {

		void displayName(String name);

		void displayCash(Double cash);

		void displayValue(Double value);

		void displayReturnPct(Double returnPct);
	}

	@Inject
	OtherUserPresenter(EventBus eventBus, MyView view) {
		super(eventBus, view);

	}

	public void setAndDisplay(UserDto user) {
		getView().displayName(user.getName());
		getView().displayCash(user.getCash());
		getView().displayValue(user.getValue());
		getView().displayReturnPct(user.getReturnPct());
	}

}
