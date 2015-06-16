package com.akolchin.stmg.shared.action;

import com.akolchin.stmg.shared.dto.UserDto;
import com.gwtplatform.dispatch.annotation.GenDispatch;
import com.gwtplatform.dispatch.annotation.In;
import com.gwtplatform.dispatch.annotation.Out;
import com.gwtplatform.dispatch.rpc.shared.UnsecuredActionImpl;

@GenDispatch(isSecure = false, serviceName = UnsecuredActionImpl.DEFAULT_SERVICE_NAME)
public class Login {

	@In(1)
	String login;
	@In(2)
	String password;

	// @Out(1) String sessionKey; //TODO recall the reason for this value
	@Out(1)
	UserDto currentUserDto;
}
