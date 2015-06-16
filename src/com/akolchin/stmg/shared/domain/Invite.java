/**
 * 
 */
package com.akolchin.stmg.shared.domain;

import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Load;

/**
 * Invites sent
 * 
 */

@Entity
@Cache
public class Invite extends DatastoreObject {

	@Index
	@Load
	// ref to Inviter user
	private Ref<AppUser> userRef; // not named "inviterRef" in order to be able to use universal method to filter by
									// current user

	String fbName;

	@Index
	String fbId;

	public Invite() {

	}

	public AppUser getInviter() {
		return userRef != null ? userRef.get() : null;
	}

	public void setInviter(AppUser user) {
		userRef = user != null ? Ref.create(user) : null;
	}

	public String getFbName() {
		return fbName;
	}

	public void setFbName(String fbName) {
		this.fbName = fbName;
	}

	public String getFbId() {
		return fbId;
	}

	public void setFbId(String fbId) {
		this.fbId = fbId;
	}

}
