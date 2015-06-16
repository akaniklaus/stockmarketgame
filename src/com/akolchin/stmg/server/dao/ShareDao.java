package com.akolchin.stmg.server.dao;

import com.akolchin.stmg.shared.domain.Share;
import com.googlecode.objectify.Objectify;

public class ShareDao extends BaseDao<Share> {

	public ShareDao() {
		super(Share.class);
	}

	public ShareDao(Objectify ofy) {
		super(Share.class, ofy);
	}

}
