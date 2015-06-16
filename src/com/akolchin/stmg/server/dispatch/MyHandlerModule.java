package com.akolchin.stmg.server.dispatch;

import com.akolchin.stmg.server.dispatch.auth.GetCurrentUserHandler;
import com.akolchin.stmg.server.dispatch.auth.LogoutHandler;
import com.akolchin.stmg.shared.action.AddStockToPortfolioAction;
import com.akolchin.stmg.shared.action.DeleteInviteAction;
import com.akolchin.stmg.shared.action.DeleteShareFromPortfolioAction;
import com.akolchin.stmg.shared.action.DoTradeAction;
import com.akolchin.stmg.shared.action.GetAffiliatesAction;
import com.akolchin.stmg.shared.action.GetCurrentUserAction;
import com.akolchin.stmg.shared.action.GetFbFriendsAction;
import com.akolchin.stmg.shared.action.GetInvitesAction;
import com.akolchin.stmg.shared.action.GetPortfolioAction;
import com.akolchin.stmg.shared.action.GetRanksAction;
import com.akolchin.stmg.shared.action.GetShareAction;
import com.akolchin.stmg.shared.action.GetStocksAction;
import com.akolchin.stmg.shared.action.InviteFbFriendAction;
import com.akolchin.stmg.shared.action.LogoutAction;
import com.gwtplatform.dispatch.rpc.server.guice.HandlerModule;

public class MyHandlerModule extends HandlerModule {
	@Override
	protected void configureHandlers() {

		// Bind Action to Action Handler

		bindHandler(GetCurrentUserAction.class, GetCurrentUserHandler.class);
		bindHandler(LogoutAction.class, LogoutHandler.class);

		bindHandler(GetStocksAction.class, GetStocksHandler.class);
		bindHandler(AddStockToPortfolioAction.class, AddStockToPortfolioHandler.class);

		bindHandler(GetPortfolioAction.class, GetPortfolioHandler.class);
		bindHandler(DeleteShareFromPortfolioAction.class, DeleteShareFromPortfolioHandler.class);
		bindHandler(DoTradeAction.class, DoTradeHandler.class);
		bindHandler(GetShareAction.class, GetShareHandler.class);
		
		bindHandler(GetRanksAction.class, GetRanksHandler.class);

		bindHandler(GetAffiliatesAction.class, GetAffiliatesHandler.class);

		bindHandler(GetFbFriendsAction.class, GetFbFriendsHandler.class);
		bindHandler(InviteFbFriendAction.class, InviteFbFriendHandler.class);
		bindHandler(GetInvitesAction.class, GetInvitesHandler.class);
		bindHandler(DeleteInviteAction.class, DeleteInviteHandler.class);

		// This fetch has a Validator which only lets App Admins fetch it.
		// bindHandler(FetchAdminTaskCountAction.class, FetchAdminTaskCountHandler.class, AdminActionValidator.class);
	}
}
