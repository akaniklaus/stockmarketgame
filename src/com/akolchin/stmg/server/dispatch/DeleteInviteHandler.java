package com.akolchin.stmg.server.dispatch;

import java.util.logging.Logger;

import javax.inject.Inject;

import com.akolchin.stmg.server.authentication.CurrentAppUserProvider;
import com.akolchin.stmg.server.dao.InviteDao;
import com.akolchin.stmg.server.dispatch.auth.AbstractHandlerWithAuth;
import com.akolchin.stmg.shared.action.DeleteInviteAction;
import com.akolchin.stmg.shared.action.DeleteInviteResult;
import com.gwtplatform.dispatch.rpc.server.ExecutionContext;
import com.gwtplatform.dispatch.shared.ActionException;

public class DeleteInviteHandler extends AbstractHandlerWithAuth<DeleteInviteAction, DeleteInviteResult> {
	private static final Logger log = Logger.getLogger(DeleteInviteHandler.class.getCanonicalName());

	@Inject
	public DeleteInviteHandler(CurrentAppUserProvider currentAppUserProvider) {
		super(DeleteInviteAction.class, currentAppUserProvider);
	}

	@Override
	public DeleteInviteResult execute(DeleteInviteAction action, ExecutionContext context) throws ActionException {

		new InviteDao().delete(action.getId());

		log.finer("Invite successfully delete ");

		return new DeleteInviteResult(true);
	}
}
