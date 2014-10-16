package org.magnum.mobilecloud.video.client;

import java.util.Collection;

import org.magnum.mobilecloud.video.repository.Member;

import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Query;

/**
 * This interface defines an API for a VideoSvc. The
 * interface is used to provide a contract for client/server
 * interactions. The interface is annotated with Retrofit
 * annotations so that clients can automatically convert the
 * 
 * 
 * @author jules
 *
 */
public interface MemberSvcApi {
	
	public static final String NAME_PARAMETER = "name";

	// The path where we expect the VideoSvc to live
	public static final String MEMBER_SVC_PATH = "/members";

	// The path to search videos by title
	public static final String MEMBER_SEARCH_PATH = MEMBER_SVC_PATH + "/find";

	@GET(MEMBER_SVC_PATH)
	public Collection<Member> getVideoList();
	
	@POST(MEMBER_SVC_PATH)
	public boolean addMember(@Body Member v);
	
	@GET(MEMBER_SEARCH_PATH)
	public Collection<Member> findByTitle(@Query(NAME_PARAMETER) String title);
	
}
