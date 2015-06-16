package com.akolchin.stmg.server.dispatch;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.servlet.http.HttpSession;

import com.akolchin.stmg.shared.action.GetFbFriendsAction;
import com.akolchin.stmg.shared.action.GetFbFriendsResult;
import com.gwtplatform.dispatch.rpc.server.ExecutionContext;
import com.gwtplatform.dispatch.shared.ActionException;

import facebook4j.Facebook;
import facebook4j.FacebookException;
import facebook4j.Friend;
import facebook4j.ResponseList;

public class GetFbFriendsHandler extends MyAbstractActionHandler<GetFbFriendsAction, GetFbFriendsResult> {

	private final Provider<HttpSession> sessionProvider;

	@Inject
	public GetFbFriendsHandler(Provider<HttpSession> sessionProvider) {
		super(GetFbFriendsAction.class);

		this.sessionProvider = sessionProvider;
	}

	@Override
	public GetFbFriendsResult execute(GetFbFriendsAction action, ExecutionContext context) throws ActionException {

		// TODO improve approach to deal with stored in session exemplar of facebook
		// TODO perhaps I have to create provider or store it as field of current user
		HttpSession session = sessionProvider.get();
		Facebook facebook = (Facebook) session.getAttribute("facebook");

		// get friends list from FB
		ResponseList<Friend> friendsList;
		try {
			friendsList = facebook.getFriends();
		} catch (FacebookException e) {
			// TODO log error
			throw new ActionException(e);
		}

		Map<String, String> friends = new HashMap<String, String>();
		for (Friend friend : friendsList) {
			friends.put(friend.getId(), friend.getName());
		}
		return new GetFbFriendsResult(friends);
	}

}
