package com.noize.server;

import java.util.ArrayList;
import java.util.Iterator;

import javax.jdo.Extent;
import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;

import com.noize.client.DatabaseService;
import com.noize.shared.Member;
import com.noize.shared.MemberSmall;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server-side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class DatabaseServiceImpl extends RemoteServiceServlet implements
		DatabaseService {
	
	private static PersistenceManagerFactory PMF = JDOHelper.getPersistenceManagerFactory("transactions-optional");

	public Member addMember(String firstname, String lastname, String email) throws IllegalArgumentException {
		// Verify that the input is valid. 
//		if (!FieldVerifier.isValidName(input)) {
//			// If the input is not valid, throw an IllegalArgumentException back to
//			// the client.
//			throw new IllegalArgumentException(
//					"Name must be at least 4 characters long");
//		}
//		 Map<String, String> properties = new HashMap();
//		    if (SystemProperty.environment.value() ==
//		          SystemProperty.Environment.Value.Production) {
//		      properties.put("javax.jdo.option.ConnectionDriverName",
//		          "com.mysql.jdbc.GoogleDriver");
//		      properties.put("javax.jdo.option.ConnectionURL",
//		          System.getProperty("cloudsql.url"));
//		    } else {
//		      properties.put("javax.jdo.option.ConnectionDriverName",
//		          "com.mysql.jdbc.Driver");
//		      properties.put("javax.jdo.option.ConnectionURL",
//		          System.getProperty("cloudsql.url.dev"));
//		    }
		    
		PersistenceManager pm = getPersistenceManager();
		Member m = new Member(firstname,lastname,email);
		MemberSmall ms = new MemberSmall(firstname + " " + lastname);
		m.setMemberSmall(ms);
		try {
			pm.makePersistent(m);
//			pm.makePersistent(ms);
		}
		finally{
			pm.close();
		}
		
		String serverInfo = getServletContext().getServerInfo();
		String userAgent = getThreadLocalRequest().getHeader("User-Agent");

		// Escape data from the client to avoid cross-site script vulnerabilities.
		firstname = escapeHtml(firstname);
		userAgent = escapeHtml(userAgent);

		return m;
	}
	
	@Override
	public boolean updateMember(Member m) {
		PersistenceManager pm = getPersistenceManager();
		try {
//			Member res = (Member) pm.getObjectById(m);
			pm.deletePersistent(m);
			pm.makePersistent(m);
		} finally{
			pm.close();
		}
		return true;
	}
	
	@Override
	public boolean deleteMember(String id) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public ArrayList<MemberSmall> getMembersSmall() {
		ArrayList<MemberSmall> list = new ArrayList<>();
		PersistenceManager pm = getPersistenceManager();
		try {
			Extent<MemberSmall> e = pm.getExtent(MemberSmall.class);
			Iterator<MemberSmall> iter = e.iterator();
			while(iter.hasNext()){
				list.add((MemberSmall) iter.next());
			}
		}
		finally {
			pm.close();
		}
		return list;
	}

	/**
	 * Escape an html string. Escaping data received from the client helps to
	 * prevent cross-site script vulnerabilities.
	 * 
	 * @param html the html string to escape
	 * @return the escaped string
	 */
	private String escapeHtml(String html) {
		if (html == null) {
			return null;
		}
		return html.replaceAll("&", "&amp;").replaceAll("<", "&lt;")
				.replaceAll(">", "&gt;");
	}
	
	private PersistenceManager getPersistenceManager() {
		return PMF.getPersistenceManager();
	}
}
