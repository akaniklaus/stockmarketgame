package com.akolchin.stmg.server.authentication;

import javax.inject.Singleton;

import com.google.inject.AbstractModule;

public class AuthenticationModule extends AbstractModule {
	@Override
	protected void configure() {
		bind(CurrentAppUserProvider.class).in(Singleton.class);
	}
}
