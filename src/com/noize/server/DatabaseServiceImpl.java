package com.noize.server;

import java.util.HashMap;
import java.util.Map;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;

import com.noize.client.DatabaseService;
import com.google.appengine.api.utils.SystemProperty;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server-side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class DatabaseServiceImpl extends RemoteServiceServlet implements
		DatabaseService {
	
	private static PersistenceManagerFactory PMF;

	public String addMember(String firstname, String lastname) throws IllegalArgumentException {
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
		PMF =  JDOHelper.getPersistenceManagerFactory("transactions-optional");
		    
		PersistenceManager pm = getPersistenceManager();
		try {
			pm.makePersistent(new Member(firstname,lastname));
		}
		finally{
			pm.close();
		}
		
		String serverInfo = getServletContext().getServerInfo();
		String userAgent = getThreadLocalRequest().getHeader("User-Agent");

		// Escape data from the client to avoid cross-site script vulnerabilities.
		firstname = escapeHtml(firstname);
		userAgent = escapeHtml(userAgent);

		return firstname + lastname + "added to Database";
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
