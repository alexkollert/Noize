package com.noize.client;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.noize.shared.Member;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface DatabaseServiceAsync {
	void addMember(String name, String lastname, AsyncCallback<Member> callback);
	void deleteMember(String id,AsyncCallback<Boolean> callback);
	void getMembers(AsyncCallback<ArrayList<Member>> callback);
}
