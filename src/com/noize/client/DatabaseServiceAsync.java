package com.noize.client;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.noize.shared.Member;
import com.noize.shared.MemberSmall;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface DatabaseServiceAsync {
	void addMember(String name, String lastname, String email,
			AsyncCallback<Member> callback);
	void deleteMember(String id,AsyncCallback<Boolean> callback);
	void getMembersSmall(AsyncCallback<ArrayList<MemberSmall>> asyncCallback);
	void updateMember(Member m, AsyncCallback<Boolean> callback);
}
