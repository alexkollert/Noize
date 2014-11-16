package com.noize.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client-side stub for the RPC service.
 */
@RemoteServiceRelativePath("add")
public interface DatabaseService extends RemoteService {
	String addMember(String name, String lastname) throws IllegalArgumentException;
}
