package com.akolchin.stmg.client.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HasHandlers;

public class PortfolioChangedEvent extends GwtEvent<PortfolioChangedEvent.PortfolioChangedHandler> {

	public static Type<PortfolioChangedHandler> TYPE = new Type<PortfolioChangedHandler>();

	public interface PortfolioChangedHandler extends EventHandler {
		void onPortfolioChanged(PortfolioChangedEvent event);
	}

	public PortfolioChangedEvent() {

	}

	@Override
	protected void dispatch(PortfolioChangedHandler handler) {
		handler.onPortfolioChanged(this);
	}

	@Override
	public Type<PortfolioChangedHandler> getAssociatedType() {
		return TYPE;
	}

	public static Type<PortfolioChangedHandler> getType() {
		return TYPE;
	}

	public static void fire(HasHandlers source) {
		source.fireEvent(new PortfolioChangedEvent());
	}
}
