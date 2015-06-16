package com.akolchin.stmg.server.dao;

import java.util.List;

import com.akolchin.stmg.shared.domain.Invite;

public class InviteDao extends BaseDao<Invite> {

	public InviteDao() {
		super(Invite.class);
	}

	public Invite getByFbId(String fbId) {

		List<Invite> invites = listAllByProperty("fbId", fbId);
		if (invites == null || invites.isEmpty())
			return null;
		else
			return invites.get(0);
		// TODO if was invited several times, select inviter by some certain reason (just select first by time?)
	}

}
