package com.akolchin.stmg.shared.stats;

import com.gwtplatform.dispatch.annotation.GenDto;
import com.gwtplatform.dispatch.annotation.Order;

@GenDto
public class TransactionSource {
	@Order(1)
	private String source;
	@Order(2)
	private String medium;
	@Order(3)
	private Long visits;
	@Order(4)
	private Long transactions;
	@Order(5)
	private Float ecomConversionRate;
}
