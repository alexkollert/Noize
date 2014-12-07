package com.noize.client;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.noize.shared.Member;

/**
 * The client-side stub for the RPC service.
 */
@RemoteServiceRelativePath("/")
public interface DatabaseService extends RemoteService {
	Member addMember(String name, String lastname, String email, String string);

	boolean deleteMember(ArrayList<Long> ids);

	ArrayList<Member> getMembers();
	
	boolean updateMember(Member m);
	
	Member getMember(Long id);
}
