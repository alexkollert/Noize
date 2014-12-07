package com.noize.client;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.noize.shared.Member;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface DatabaseServiceAsync {
	void addMember(String name, String lastname, String email,
			String string, AsyncCallback<Member> callback);
	void deleteMember(ArrayList<Long> ids, AsyncCallback<Boolean> callback);
	void getMembers(AsyncCallback<ArrayList<Member>> asyncCallback);
	void updateMember(Member m, AsyncCallback<Boolean> callback);
	void getMember(Long id, AsyncCallback<Member> callback);
}
