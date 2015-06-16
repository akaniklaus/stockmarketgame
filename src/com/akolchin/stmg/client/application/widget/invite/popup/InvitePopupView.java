package com.akolchin.stmg.client.application.widget.invite.popup;

import java.util.Map;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.PopupViewWithUiHandlers;

public class InvitePopupView extends PopupViewWithUiHandlers<InvitePopupUiHandlers> implements InvitePopupPresenter.MyView {
	public interface Binder extends UiBinder<PopupPanel, InvitePopupView> {
	}

	@UiField
	Panel mainPanel;

	@UiField
	ListBox friendsListBox;

	@UiField
	Button inviteButton;

	@Inject
	InvitePopupView(Binder uiBinder, EventBus eventBus) {
		super(eventBus);

		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public void displayFriends(Map<String, String> friends) {
		for (String friendId : friends.keySet())
			friendsListBox.addItem(friends.get(friendId), friendId);

		friendsListBox.setVisible(true);

	}

	@UiHandler("friendsListBox")
	public void onFriendsListBoxChange(ChangeEvent event) {
		inviteButton.setText("Invite '" + friendsListBox.getItemText(friendsListBox.getSelectedIndex()) + "'");
		inviteButton.setEnabled(true);
	}

	@UiHandler("inviteButton")
	public void onInviteButtonClick(ClickEvent event) {
		int selectedIndex = friendsListBox.getSelectedIndex();
		getUiHandlers().onInviteButtonClick(friendsListBox.getValue(selectedIndex),
				friendsListBox.getItemText(selectedIndex));
		hide();
	}

}
