package com.akolchin.stmg.server.dao;

import com.akolchin.stmg.shared.TooManyResultsException;
import com.akolchin.stmg.shared.domain.Stock;
import com.googlecode.objectify.Objectify;

public class StockDao extends BaseDao<Stock> {

	public StockDao() {
		super(Stock.class);
	}

	public StockDao(Objectify ofy) {
		super(Stock.class, ofy);
	}

	public Stock getBySymbol(String symbol) throws TooManyResultsException {
		return getByProperty("symbol", symbol);
	}

}
