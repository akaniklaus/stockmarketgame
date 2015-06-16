package com.akolchin.stmg.server.dao;

import java.util.List;

import com.akolchin.stmg.shared.TooManyResultsException;
import com.akolchin.stmg.shared.domain.AppUser;

public class AppUserDao extends BaseDao<AppUser> {

	private static final int TOP_N = 5;

	public AppUserDao() {
		super(AppUser.class);

		updateRank();
	}

	public List<AppUser> listAll() {
		return super.listAll();
	}

	/*
	 * List all users invited by inviter
	 */
	public List<AppUser> listAll(AppUser inviter) {
		return super.listAllByProperty("inviterRef", inviter);
	}

	public void save(AppUser appUser) {
		super.save(appUser);
	}

	public AppUser saveAndReturn(AppUser appUser) {
		return super.saveAndReturn(appUser);
	}

	public void delete(AppUser appUser) {
		super.delete(appUser);
	}

	public AppUser findByGoogleId(String googleId) throws TooManyResultsException {
		return super.getByProperty("googleId", googleId);
	}

	public AppUser findByEmail(String email) throws TooManyResultsException {
		return super.getByProperty("email", email);
	}

	public List<AppUser> listTopByRank() {
		return super.listTopByProperty(TOP_N, "rank");
	}

	private void updateRank() {// util method
		List<AppUser> users = listAll();
		for (AppUser user : users)
			if (user.getRank() == null)
				user.setRank(Long.MAX_VALUE);

		saveAndReturn(users);
	}

}
