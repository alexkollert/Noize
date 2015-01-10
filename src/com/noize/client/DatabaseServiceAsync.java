package com.noize.client;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.noize.shared.Member;
import com.noize.shared.Training;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface DatabaseServiceAsync {
	void addMember(String name, String lastname, String email,
			String string, AsyncCallback<Member> callback);
	void deleteMember(ArrayList<Long> ids, AsyncCallback<Boolean> callback);
	void getMembers(AsyncCallback<List<Member>> asyncCallback);
	void updateMember(Member m, AsyncCallback<Boolean> callback);
	void getMember(Long id, AsyncCallback<Member> callback);
	void addTraining(Date t, AsyncCallback<Boolean> callback);
	void getTrainingAll(AsyncCallback<List<Training>> callback);
	void storeDayinMember(Member m, String t, AsyncCallback<Void> callback);
}
