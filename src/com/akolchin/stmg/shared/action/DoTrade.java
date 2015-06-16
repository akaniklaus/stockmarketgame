package com.akolchin.stmg.shared.action;

import com.akolchin.stmg.shared.TradeAction;
import com.gwtplatform.dispatch.annotation.GenDispatch;
import com.gwtplatform.dispatch.annotation.In;
import com.gwtplatform.dispatch.annotation.Out;
import com.gwtplatform.dispatch.rpc.shared.UnsecuredActionImpl;

@GenDispatch(isSecure = false, serviceName = UnsecuredActionImpl.DEFAULT_SERVICE_NAME)
public class DoTrade {

	@In(1)
	TradeAction action;
	@In(2)
	String symbol;
	@In(3)
	Double amount;

	@Out(1)
	Boolean isSuccess; 
}
