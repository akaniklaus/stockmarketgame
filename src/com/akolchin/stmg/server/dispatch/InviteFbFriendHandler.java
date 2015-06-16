package com.akolchin.stmg.server.dispatch;

import java.util.logging.Logger;

import javax.inject.Inject;

import com.akolchin.stmg.server.authentication.CurrentAppUserProvider;
import com.akolchin.stmg.server.dao.InviteDao;
import com.akolchin.stmg.server.dispatch.auth.AbstractHandlerWithAuth;
import com.akolchin.stmg.shared.action.InviteFbFriendAction;
import com.akolchin.stmg.shared.action.InviteFbFriendResult;
import com.akolchin.stmg.shared.domain.Invite;
import com.gwtplatform.dispatch.rpc.server.ExecutionContext;
import com.gwtplatform.dispatch.shared.ActionException;

public class InviteFbFriendHandler extends AbstractHandlerWithAuth<InviteFbFriendAction, InviteFbFriendResult> {
	private static final Logger log = Logger.getLogger(DoTradeHandler.class.getCanonicalName());

	@Inject
	public InviteFbFriendHandler(CurrentAppUserProvider currentAppUserProvider) {
		super(InviteFbFriendAction.class, currentAppUserProvider);
	}

	@Override
	public InviteFbFriendResult execute(InviteFbFriendAction action, ExecutionContext context) throws ActionException {
		// TODO somehow actually invite Friend (at list by email?)

		// TODO process invitation at server side: add invited user record to DB
		Invite invite = new Invite();
		invite.setInviter(getCurrenAppUser());
		invite.setFbName(action.getInvitedName());
		invite.setFbId(action.getInvitedId());

		invite = new InviteDao().saveAndReturn(invite);
		
		log.finer("Invite stored successfully, Invite to  " + action.getInvitedName());

		return new InviteFbFriendResult(invite);
	}

}
