package com.akolchin.stmg.client.application.widget.invite.popup;

import com.gwtplatform.mvp.client.UiHandlers;

public interface InvitePopupUiHandlers extends UiHandlers {

	void onInviteButtonClick(String invitedId, String invitedName);
}
