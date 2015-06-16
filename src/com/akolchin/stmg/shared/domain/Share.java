package com.akolchin.stmg.shared.domain;

import com.akolchin.stmg.server.dao.StockDao;
import com.akolchin.stmg.shared.TooManyResultsException;
import com.google.gwt.core.shared.GwtIncompatible;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Ignore;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Load;
import com.googlecode.objectify.annotation.OnLoad;
import com.googlecode.objectify.annotation.OnSave;

/**
 * users can add stocks they are interested into the Portfolio (This does not mean he bought them) After he adds, user
 * can buy or sell stock shares
 */

@Entity
@Cache
public class Share extends DatastoreObject {

	@Index
	@Load
	private Ref<AppUser> userRef;

	@Index
	private String symbol;

	@Ignore
	private Stock stock;

	@OnLoad
	@OnSave
	@GwtIncompatible
	private void onLoadOrSaveUpdateStockField() throws TooManyResultsException {
		// probably loading stock or event some values from stock by symbol can be more appropriate then use Ref

		// TODO is it really has sense to use the same ofy here?
		stock = new StockDao().getBySymbol(symbol);

	}

	private Double shares = 0d;

	public Share() {

	}

	public AppUser getUser() {
		return userRef != null ? userRef.get() : null;
	}

	public void setUser(AppUser user) {
		userRef = user != null ? Ref.create(user) : null;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public Stock getStock() {
		return stock;
	}

	public Double getShares() {
		return shares;
	}

	public void setShares(Double shares) {
		this.shares = shares;
	}

}
