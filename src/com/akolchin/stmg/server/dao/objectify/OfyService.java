package com.akolchin.stmg.server.dao.objectify;

import com.akolchin.stmg.shared.domain.AppUser;
import com.akolchin.stmg.shared.domain.Invite;
import com.akolchin.stmg.shared.domain.Share;
import com.akolchin.stmg.shared.domain.Stock;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.ObjectifyService;

public class OfyService {
	static {
		factory().register(AppUser.class);
		factory().register(Stock.class);
		factory().register(Share.class);
		factory().register(Invite.class);
	}

	public static Objectify ofy() {
		return ObjectifyService.ofy();
	}

	public static ObjectifyFactory factory() {
		return ObjectifyService.factory();
	}
}
