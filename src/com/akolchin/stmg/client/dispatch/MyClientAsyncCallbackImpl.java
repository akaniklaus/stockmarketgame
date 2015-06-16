package com.akolchin.stmg.client.dispatch;

import com.akolchin.stmg.client.GwtUtils;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

public abstract class MyClientAsyncCallbackImpl<T> implements AsyncCallback<T> {
	@Override
	public void onFailure(Throwable caught) {
		Window.alert(GwtUtils.onServerFailureMsg());
	}
}
