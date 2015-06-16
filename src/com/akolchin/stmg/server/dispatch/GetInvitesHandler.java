package com.akolchin.stmg.server.dispatch;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import com.akolchin.stmg.server.authentication.CurrentAppUserProvider;
import com.akolchin.stmg.server.dao.InviteDao;
import com.akolchin.stmg.server.dispatch.auth.AbstractHandlerWithAuth;
import com.akolchin.stmg.shared.action.GetInvitesAction;
import com.akolchin.stmg.shared.action.GetInvitesResult;
import com.akolchin.stmg.shared.domain.Invite;
import com.gwtplatform.dispatch.rpc.server.ExecutionContext;
import com.gwtplatform.dispatch.shared.ActionException;

public class GetInvitesHandler extends AbstractHandlerWithAuth<GetInvitesAction, GetInvitesResult> {

	@Inject
	public GetInvitesHandler(CurrentAppUserProvider currentAppUserProvider) {
		super(GetInvitesAction.class, currentAppUserProvider);
	}

	@Override
	public GetInvitesResult execute(GetInvitesAction action, ExecutionContext context) throws ActionException {

		List<Invite> invites = new ArrayList<Invite>(new InviteDao().listAll(getCurrenAppUser()));
		return new GetInvitesResult(invites);
	}

}
