package com.noize.client;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.noize.shared.FinanceMonth;
import com.noize.shared.FinanceYear;
import com.noize.shared.Member;
import com.noize.shared.MemberToFinances;
import com.noize.shared.MemberToTraining;
import com.noize.shared.Training;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface DatabaseServiceAsync {
	void addMember(String name, String lastname, String email, int role,
			String address, Date birthdate, int job,
			AsyncCallback<Member> callback);
	void deleteMember(ArrayList<Long> ids, AsyncCallback<Boolean> callback);
	void getMembers(AsyncCallback<List<Member>> asyncCallback);
	void updateMember(Member m, AsyncCallback<Boolean> callback);
	void getMember(Long id, AsyncCallback<Member> callback);
	void addTraining(Date t, AsyncCallback<Boolean> callback);
	void getTrainingAll(AsyncCallback<List<Training>> callback);
//	void storeDayinMember(Member m, AsyncCallback<Void> callback);
	void getTraining(String id, AsyncCallback<Training> callback);
	void addMemberToTraining(MemberToTraining mtt, AsyncCallback<Void> callback);
	void getMemberToTrainingAll(AsyncCallback<List<MemberToTraining>> callback);
	void deleteMemberToTraining(Long id, AsyncCallback<Void> callback);
	void deleteTraining(Long id, AsyncCallback<Void> callback);
	void getMemberToFinances(AsyncCallback<List<MemberToFinances>> callback);
	void addMemberToFinances(MemberToFinances mtf, AsyncCallback<Void> callback);
	void deleteMemberToFinances(Long id, AsyncCallback<Void> callback);
	void addNewFinancesYear(int year, AsyncCallback<Void> callback);
	void getFinancesMonths(AsyncCallback<List<FinanceMonth>> callback);
	void getFinanceYears(AsyncCallback<List<FinanceYear>> callback);
}
