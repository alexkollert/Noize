package com.noize.client;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.noize.shared.Member;
import com.noize.shared.MemberSmall;

/**
 * The client-side stub for the RPC service.
 */
@RemoteServiceRelativePath("/")
public interface DatabaseService extends RemoteService {
	Member addMember(String name, String lastname,String email);

	boolean deleteMember(ArrayList<Long> ids);

	ArrayList<MemberSmall> getMembersSmall();
	
	boolean updateMember(Member m);
}
