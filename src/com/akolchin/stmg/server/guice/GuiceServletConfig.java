package com.akolchin.stmg.server.guice;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;

public class GuiceServletConfig extends GuiceServletContextListener {
	@Override
	protected Injector getInjector() {

		// Important: GaeStudioModule needs to be the last one.

		return Guice.createInjector(new ServerModule(), new MyServletModule());// , new GaeStudioModule());

	}
}
