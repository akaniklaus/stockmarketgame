package com.akolchin.stmg.shared.stats;

import com.gwtplatform.dispatch.annotation.GenDto;
import com.gwtplatform.dispatch.annotation.Order;

@GenDto
public class ReferralSite {
	@Order(1)
	private String site;
	@Order(2)
	private Long visits;
}
