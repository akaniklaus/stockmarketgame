package com.akolchin.stmg.shared.dto;

public class UserDto implements Dto {

	private boolean loggedIn;

	private String email;

	private String name;

	private Double cash;
	private Double value;
	private Double returnPct;

	private Long rank;

	public UserDto() {
		// not logged in user default values

		loggedIn = false;

	}

	public void setAttributes(Boolean isLoggedIn, String email, String name, Double cash, Double value,
			Double returnPct, Long rank) {
		this.loggedIn = isLoggedIn;
		this.email = email;
		this.name = name;
		this.cash = cash;
		this.value = value;
		this.returnPct = returnPct;
		this.rank = rank;
	}

	public boolean isLoggedIn() {
		return loggedIn;
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

	public void setValue(Double value) {
		this.value = value;
	}

	public Double getReturnPct() {
		return returnPct;
	}

	public void setReturnPct(Double returnPct) {
		this.returnPct = returnPct;
	}

	public Long getRank() {
		return rank;
	}

	// public void setRank(Long rank) {
	// this.rank = rank;
	// }
	// TODO id setters not needed remove all setters

	public void copyFrom(UserDto userToCopy) {
		loggedIn = userToCopy.loggedIn;
		email = userToCopy.email;
		name = userToCopy.name;
		cash = userToCopy.cash;
		value = userToCopy.value;
		returnPct = userToCopy.returnPct;
		rank = userToCopy.rank;
	}

}
