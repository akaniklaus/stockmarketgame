package com.akolchin.stmg.client.application.search;

public interface TimerInterface {
	public abstract void run();

	public abstract void setPresenter(SearchPresenter presenter);

	public abstract void start();

	public abstract void stop();

}
