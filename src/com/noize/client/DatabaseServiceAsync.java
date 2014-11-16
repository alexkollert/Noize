package com.noize.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface DatabaseServiceAsync {
	void addMember(String name, String lastname, AsyncCallback<String> callback);
}
