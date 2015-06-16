package com.akolchin.stmg.shared.action;

import com.akolchin.stmg.shared.domain.Share;
import com.gwtplatform.dispatch.annotation.GenDispatch;
import com.gwtplatform.dispatch.annotation.In;
import com.gwtplatform.dispatch.annotation.Out;
import com.gwtplatform.dispatch.rpc.shared.UnsecuredActionImpl;

@GenDispatch(isSecure = false, serviceName = UnsecuredActionImpl.DEFAULT_SERVICE_NAME)
public class AddStockToPortfolio {

	@In(1)
	String symbol;

	@Out(1)
	Share share;
}
