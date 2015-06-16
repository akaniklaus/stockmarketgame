package com.akolchin.stmg.shared.domain;

import java.util.List;

import com.akolchin.stmg.server.dao.ShareDao;
import com.google.gwt.core.shared.GwtIncompatible;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Ignore;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Load;
import com.googlecode.objectify.annotation.OnLoad;

/**
 * An application user, named with a prefix to avoid confusion with GAE User type
 */

@Entity
public class AppUser extends DatastoreObject {

	private static final long serialVersionUID = -5874192599555268073L;

	@Index
	private String email;

	private String name;

	private Double cash; //

	@Ignore
	private Double value;
	@Ignore
	private Double returnPct;

	@Index
	@Load
	private Ref<AppUser> inviterRef;

	@Index
	private Long rank = Long.MAX_VALUE; //put the new users at the lowest places

	@OnLoad
	@GwtIncompatible
	private void onLoadCalculateValues() {
		// TODO is it really has sense to use the same ofy here?
		value = 0d;
		List<Share> shares = new ShareDao().listAll(this);
		for (Share share : shares)
			value += share.getShares() * share.getStock().getLastTradePrice();
		returnPct = ((value + cash) / 10000) * 100;
	}

	public AppUser() {

	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getCash() {
		return cash;
	}

	public void setCash(Double cash) {
		this.cash = cash;
	}

	public Double getValue() {
		return value;
	}

	public Double getReturnPct() {
		return returnPct;
	}

	public AppUser getInviter() {
		return inviterRef != null ? inviterRef.get() : null;
	}

	public void setInviter(AppUser user) {
		inviterRef = user != null ? Ref.create(user) : null;
	}

	public Long getRank() {
		return rank;
	}

	public void setRank(Long rank) {
		this.rank = rank;
	}

	@Override
	public String toString() {
		return "AppUser [email=" + email + ", name=" + name + ", cash=" + cash + ", value=" + value + ", returnPct="
				+ returnPct + ", inviterRef=" + inviterRef + ", rank=" + rank + "]";
	}

}
