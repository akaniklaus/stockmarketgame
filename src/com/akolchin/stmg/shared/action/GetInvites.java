package com.akolchin.stmg.shared.action;

import java.util.List;

import com.akolchin.stmg.shared.domain.Invite;
import com.gwtplatform.dispatch.annotation.GenDispatch;
import com.gwtplatform.dispatch.annotation.Out;
import com.gwtplatform.dispatch.rpc.shared.UnsecuredActionImpl;

@GenDispatch(isSecure = false, serviceName = UnsecuredActionImpl.DEFAULT_SERVICE_NAME)
public class GetInvites {

	@Out(1)
	List<Invite> invites;
}
