package com.akolchin.stmg.client.application.widget;

import com.akolchin.stmg.client.application.widget.invite.popup.InvitePopupModule;
import com.akolchin.stmg.client.application.widget.login.LoginModule;
import com.akolchin.stmg.client.application.widget.otheruser.OtherUserModule;
import com.akolchin.stmg.client.application.widget.share.ShareModule;
import com.akolchin.stmg.client.application.widget.stock.StockModule;
import com.akolchin.stmg.client.application.widget.stock.trade.TradeModule;
import com.google.gwt.inject.client.AbstractGinModule;
import com.akolchin.stmg.client.application.widget.invite.InviteModule;

public class WidgetModule extends AbstractGinModule {
	@Override
	protected void configure() {
		install(new OtherUserModule());
		install(new InviteModule());
		install(new StockModule());
		install(new ShareModule());
		install(new TradeModule());
		
		install(new InvitePopupModule());

		install(new LoginModule());
	}
}
