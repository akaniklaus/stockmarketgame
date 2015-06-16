package com.akolchin.stmg.client.event;

import com.akolchin.stmg.shared.domain.Share;
import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HasHandlers;

public class ShareAddedToPortfolioEvent extends GwtEvent<ShareAddedToPortfolioEvent.ShareAddedToPortfolioHandler> {

	public static Type<ShareAddedToPortfolioHandler> TYPE = new Type<ShareAddedToPortfolioHandler>();

	private final Share share;

	public interface ShareAddedToPortfolioHandler extends EventHandler {
		void onShareAddedToPortfolio(ShareAddedToPortfolioEvent event);
	}

	public ShareAddedToPortfolioEvent(Share share) {
		this.share = share;
	}

	@Override
	protected void dispatch(ShareAddedToPortfolioHandler handler) {
		handler.onShareAddedToPortfolio(this);
	}

	@Override
	public Type<ShareAddedToPortfolioHandler> getAssociatedType() {
		return TYPE;
	}

	public static Type<ShareAddedToPortfolioHandler> getType() {
		return TYPE;
	}

	public static void fire(HasHandlers source, Share share) {
		source.fireEvent(new ShareAddedToPortfolioEvent(share));
	}

	public Share getShare() {
		return share;
	}
}
