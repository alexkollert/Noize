package com.noize.client;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.noize.shared.Member;
import com.noize.shared.MemberToTraining;
import com.noize.shared.Training;

/**
 * The client-side stub for the RPC service.
 */
@RemoteServiceRelativePath("/")
public interface DatabaseService extends RemoteService {
	Member addMember(String name, String lastname, String email, String string);

	boolean deleteMember(ArrayList<Long> ids);

	List<Member> getMembers();
	
	boolean updateMember(Member m);
	
	Member getMember(Long id);
	
	boolean addTraining(Date t);
	
	List<Training> getTrainingAll();
	
	void storeDayinMember(Member m);
	
	Training getTraining(String id);
	
	void addMemberToTraining(MemberToTraining mtt);
	
	List<MemberToTraining> getMemberToTrainingAll();
}
