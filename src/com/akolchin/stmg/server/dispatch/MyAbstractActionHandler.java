package com.akolchin.stmg.server.dispatch;

import com.gwtplatform.dispatch.rpc.server.ExecutionContext;
import com.gwtplatform.dispatch.rpc.server.actionhandler.AbstractActionHandler;
import com.gwtplatform.dispatch.rpc.shared.Action;
import com.gwtplatform.dispatch.rpc.shared.Result;
import com.gwtplatform.dispatch.shared.ActionException;

/**
 * Simple abstract super-class for {@link com.gwtplatform.dispatch.server.actionhandler.ActionHandler} implementations
 * that forces the {@link com.gwtplatform.dispatch.shared.Action} class to be passed in as a constructor to the handler.
 * 
 * 
 * @param <A>
 *            The {@link com.gwtplatform.dispatch.shared.Action} type.
 * @param <R>
 *            The {@link com.gwtplatform.dispatch.shared.Result} type.
 */

public abstract class MyAbstractActionHandler<A extends Action<R>, R extends Result> extends
		AbstractActionHandler<A, R> {

	public MyAbstractActionHandler(Class<A> actionType) {
		super(actionType);
	}

	@Override
	public void undo(A action, R result, ExecutionContext context) throws ActionException {
	}
}
