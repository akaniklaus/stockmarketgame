package com.akolchin.stmg.shared.action;

import com.akolchin.stmg.shared.domain.Invite;
import com.gwtplatform.dispatch.annotation.GenDispatch;
import com.gwtplatform.dispatch.annotation.In;
import com.gwtplatform.dispatch.annotation.Out;
import com.gwtplatform.dispatch.rpc.shared.UnsecuredActionImpl;

@GenDispatch(isSecure = false, serviceName = UnsecuredActionImpl.DEFAULT_SERVICE_NAME)
public class InviteFbFriend {

	@In(1)
	String invitedId;
	@In(2)
	String invitedName;


	@Out(1)
	Invite invited;

}
