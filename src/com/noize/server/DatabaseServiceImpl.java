package com.noize.server;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.jdo.Extent;
import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;

import com.noize.client.DatabaseService;
import com.noize.shared.Member;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server-side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class DatabaseServiceImpl extends RemoteServiceServlet implements
		DatabaseService {
	
	private static PersistenceManagerFactory PMF = JDOHelper.getPersistenceManagerFactory("transactions-optional");

	public Member addMember(String firstname, String lastname, String email,String role) throws IllegalArgumentException {
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
		pm.currentTransaction().begin();
		Member m = new Member(firstname,lastname,email,role);
		try {
			pm.makePersistent(m);
//			pm.makePersistent(ms);
			pm.currentTransaction().commit();
		}
		catch(Exception e){
			pm.currentTransaction().rollback();
		}
		finally{
			pm.close();
		}
		
//		String serverInfo = getServletContext().getServerInfo();
//		String userAgent = getThreadLocalRequest().getHeader("User-Agent");

		// Escape data from the client to avoid cross-site script vulnerabilities.
//		firstname = escapeHtml(firstname);
//		userAgent = escapeHtml(userAgent);

		return m;
	}
	
	@Override
	public boolean updateMember(Member m) {
		PersistenceManager pm = getPersistenceManager();
//		pm.currentTransaction().begin();
//		pm.currentTransaction().setOptimistic(true);
		try {
			Long id = m.getId();
			if(id != null){
				pm.currentTransaction().begin();
				Query q = pm.newQuery(Member.class, "id == " + id);
				q.deletePersistentAll();
				pm.currentTransaction().commit();
			}
			pm.currentTransaction().begin();
			pm.makePersistent(m);
			pm.currentTransaction().commit();
		}catch(Exception e){
			pm.currentTransaction().rollback();
		}
		finally{
			pm.close();
		}
		return true;
	}
	
	@Override
	public boolean deleteMember(ArrayList<Long> ids) {
		PersistenceManager pm = getPersistenceManager();
		pm.currentTransaction().begin();
		try {
			for(Long id : ids){
				Query q = pm.newQuery(Member.class, "id == " + id);
				q.deletePersistentAll();
			}
			pm.currentTransaction().commit();
		} catch (Exception e) {
			pm.currentTransaction().rollback();
		}
		finally{
			pm.close();
		}
		return true;
	}
	
	@Override
	public ArrayList<Member> getMembers() {
		ArrayList<Member> list = new ArrayList<>();
		PersistenceManager pm = getPersistenceManager();
		Extent<Member> e = pm.getExtent(Member.class);
		try {
			Iterator<Member> iter = e.iterator();
			while(iter.hasNext()){
				list.add((Member) iter.next());
			}
		}
		finally {
			e.closeAll();
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

	@Override
	public Member getMember(Long id) {
		List<Member> mlist;
		PersistenceManager pm = getPersistenceManager();
//		pm.currentTransaction().begin();
		try{
			Query q = pm.newQuery(Member.class,"id == " + id);
			mlist = (List<Member>) q.execute();
//			pm.currentTransaction().commit();
		}
		finally{
			pm.close();
		}
		return mlist.get(0);
	}
}
