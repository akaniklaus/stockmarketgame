package com.akolchin.stmg.server.guice;

import com.akolchin.stmg.server.authentication.AuthenticationModule;
import com.akolchin.stmg.server.dispatch.MyHandlerModule;
import com.google.inject.AbstractModule;

public class ServerModule extends AbstractModule {
	@Override
	protected void configure() {
		install(new MyHandlerModule());
		install(new AuthenticationModule());
	}
}
