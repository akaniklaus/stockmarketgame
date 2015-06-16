package com.akolchin.stmg.server.guice;

import javax.inject.Singleton;

import com.akolchin.stmg.server.fb.CallbackServlet;
import com.akolchin.stmg.server.fb.LogoutServlet;
import com.akolchin.stmg.server.fb.PostServlet;
import com.akolchin.stmg.server.fb.SignInServlet;
import com.akolchin.stmg.server.update.UpdaterServlet;
import com.google.inject.servlet.ServletModule;
import com.googlecode.objectify.ObjectifyFilter;
import com.gwtplatform.dispatch.rpc.server.guice.DispatchServiceImpl;
import com.gwtplatform.dispatch.rpc.shared.ActionImpl;

public class MyServletModule extends ServletModule {
	@Override
	public void configureServlets() {
		// GWT-platform commands servlet
		serve("/" + ActionImpl.DEFAULT_SERVICE_NAME + "*").with(DispatchServiceImpl.class);

		// Objectify filter
		bind(ObjectifyFilter.class).in(Singleton.class);
		filter("/*").through(ObjectifyFilter.class);

		// Stock quotas updater servlet
		bind(UpdaterServlet.class).in(Singleton.class);
		serve("/update").with(UpdaterServlet.class);

		// -- FB srvlets

		bind(SignInServlet.class).in(Singleton.class);
		serve("/fb_signin").with(SignInServlet.class);

		bind(CallbackServlet.class).in(Singleton.class);
		serve("/fb_callback").with(CallbackServlet.class);

		bind(LogoutServlet.class).in(Singleton.class);
		serve("/logout").with(LogoutServlet.class);

		bind(PostServlet.class).in(Singleton.class);
		serve("/post").with(PostServlet.class);

	}
}
