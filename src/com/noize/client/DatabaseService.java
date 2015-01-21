package com.noize.client;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.noize.shared.FinanceMonth;
import com.noize.shared.Member;
import com.noize.shared.MemberToFinances;
import com.noize.shared.MemberToTraining;
import com.noize.shared.Training;

/**
 * The client-side stub for the RPC service.
 */
@RemoteServiceRelativePath("/")
public interface DatabaseService extends RemoteService {
	Member addMember(String name, String lastname, String email, int role,String address,Date birthdate,int job);

	boolean deleteMember(ArrayList<Long> ids);

	List<Member> getMembers();
	
	boolean updateMember(Member m);
	
	Member getMember(Long id);
	
	boolean addTraining(Date t);
	
	List<Training> getTrainingAll();
	
//	void storeDayinMember(Member m);
	
	Training getTraining(String id);
	
	void addMemberToTraining(MemberToTraining mtt);
	
	List<MemberToTraining> getMemberToTrainingAll();
	
	void deleteMemberToTraining(Long id);
	
	void deleteTraining(Long id);
	
	List<MemberToFinances> getMemberToFinancesAll();
	
	void addMemberToFinances(MemberToFinances mtf);
	
	void deleteMemberToFinances(Long id);
	
	void addNewFinancesYear(int year);
	
	List<FinanceMonth> getFinancesMonthAll();
	
}
