package com.akolchin.stmg.shared.domain;

import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;

/**
 * all available stocks with last quotas
 * 
 */

@Entity
@Cache
public class Stock extends DatastoreObject {

	String name;

	@Index
	String symbol;

	// @Translate(value = LocalDateTimeTranslatorFactory.class)
	// LocalDateTime lastTradeDateTime;

	Double lastTradePrice;

	Double priceChange;
	Double priceChangeInPercent;

	// BID = "Bid"; // b
	// ASK = "Ask"; // a

	// RT_LAST_TRADE = "Last Trade (Real-time) with Time"; // k1
	// RT_BID = "Bid (Real-time)"; // b3
	// RT_ASK = "Ask (Real-time)"; // b2
	// RT_CHANGE_IN_PERCENT = "Change Percent (Real-time)"; // k2
	// RT_CHANGE = "Change (Real-time)"; // c6

	// PREV_CLOSE = "Previous Close"; // p
	// OPEN = "Open"; // o

	public Stock() {

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	// public LocalDateTime getLastTradeDateTime() {
	// return lastTradeDateTime;
	// }
	//
	// public Long getLastTradeDateTimeAsMillis() {
	// return (lastTradeDateTime != null) ? lastTradeDateTime.toDateTime(DateTimeZone.UTC).getMillis() : null;
	// }
	//
	// public void setLastTradeDateTime(LocalDateTime lastTradeDateTime) {
	// this.lastTradeDateTime = lastTradeDateTime;
	// }

	public Double getLastTradePrice() {
		return lastTradePrice;
	}

	public void setLastTradePrice(Double lastTradePrice) {
		this.lastTradePrice = lastTradePrice;
	}

	public Double getPriceChange() {
		return priceChange;
	}

	public void setPriceChange(Double priceChange) {
		this.priceChange = priceChange;
	}

	public Double getPriceChangeInPercent() {
		return priceChangeInPercent;
	}

	public void setPriceChangeInPercent(Double priceChangeInPercent) {
		this.priceChangeInPercent = priceChangeInPercent;
	}

	@Override
	public String toString() {
		return "Stock [name=" + name
				+ ", symbol="
				+ symbol// + ", lastTradeDateTime=" + lastTradeDateTime
				+ ", lastTradePrice=" + lastTradePrice + ", priceChange=" + priceChange + ", priceChangeInPercent="
				+ priceChangeInPercent + ", getId()=" + getId() + ", getVersion()=" + getVersion() + ", isSaved()="
				+ isSaved() + "]";
	}

}
