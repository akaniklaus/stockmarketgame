package com.akolchin.stmg.server.dispatch;

import java.util.ArrayList;
import java.util.List;

import com.akolchin.stmg.server.dao.StockDao;
import com.akolchin.stmg.shared.action.GetStocksAction;
import com.akolchin.stmg.shared.action.GetStocksResult;
import com.akolchin.stmg.shared.domain.Stock;
import com.gwtplatform.dispatch.rpc.server.ExecutionContext;
import com.gwtplatform.dispatch.shared.ActionException;

public class GetStocksHandler extends MyAbstractActionHandler<GetStocksAction, GetStocksResult> {

	public GetStocksHandler() {
		super(GetStocksAction.class);
	}

	@Override
	public GetStocksResult execute(GetStocksAction action, ExecutionContext context) throws ActionException {

		List<Stock> stocks = new ArrayList<Stock>(new StockDao().listAll());
		return new GetStocksResult(stocks);
	}

}
