package com.akolchin.stmg.client.event;

import com.akolchin.stmg.shared.domain.Share;
import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HasHandlers;

public class ShareDeletedFromPortfolioEvent extends
		GwtEvent<ShareDeletedFromPortfolioEvent.ShareDeletedFromPortfolioHandler> {

	public static Type<ShareDeletedFromPortfolioHandler> TYPE = new Type<ShareDeletedFromPortfolioHandler>();

	private final Share share;

	public interface ShareDeletedFromPortfolioHandler extends EventHandler {
		void onShareDeletedFromPortfolio(ShareDeletedFromPortfolioEvent event);
	}

	public ShareDeletedFromPortfolioEvent(Share share) {
		this.share = share;
	}

	@Override
	protected void dispatch(ShareDeletedFromPortfolioHandler handler) {
		handler.onShareDeletedFromPortfolio(this);
	}

	@Override
	public Type<ShareDeletedFromPortfolioHandler> getAssociatedType() {
		return TYPE;
	}

	public static Type<ShareDeletedFromPortfolioHandler> getType() {
		return TYPE;
	}

	public static void fire(HasHandlers source, Share share) {
		source.fireEvent(new ShareDeletedFromPortfolioEvent(share));
	}

	public Share getShare() {
		return share;
	}
}
