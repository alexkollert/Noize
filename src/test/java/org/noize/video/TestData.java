package org.noize.video;

import java.util.UUID;

import org.noize.repository.Member;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * This is a utility class to aid in the construction of
 * Video objects with random names, urls, and durations.
 * The class also provides a facility to convert objects
 * into JSON using Jackson, which is the format that the
 * VideoSvc controller is going to expect data in for
 * integration testing.
 * 
 * @author jules
 *
 */
public class TestData {

	private static final ObjectMapper objectMapper = new ObjectMapper();
	
	/**
	 * Construct and return a Video object with a
	 * rnadom name, url, and duration.
	 * 
	 * @return
	 */
	public static Member randomMember() {
		// Information about the video
		// Construct a random identifier using Java's UUID class
		String id = UUID.randomUUID().toString();
		String title = "Member-"+id;
		String role = "http://coursera.org/some/video-"+id;
		String birthday = "28.04.1988"; // random time up to 1hr
		return new Member(title, role, birthday);
	}
	
	/**
	 *  Convert an object to JSON using Jackson's ObjectMapper
	 *  
	 * @param o
	 * @return
	 * @throws Exception
	 */
	public static String toJson(Object o) throws Exception{
		return objectMapper.writeValueAsString(o);
	}
}
