package com.akolchin.stmg.client.application.search;

import com.google.gwt.user.client.Timer;

public class SearchTimer extends Timer implements TimerInterface {

	private int delayMillis = 30 * 1000; //every 30 seconds
	private boolean active = false;

	private SearchPresenter presenter;

	@Override
	public void run() {
		if (active) {
			presenter.refresh();
			schedule(delayMillis);
		}
	}

	@Override
	public void setPresenter(SearchPresenter presenter) {
		this.presenter = presenter;
	}

	@Override
	public void start() {
		active = true;
		super.schedule(delayMillis);
	}

	@Override
	public void stop() {
		active = false;
	}

}
