package com.noize.server;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.jdo.Extent;
import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;

import com.noize.client.DatabaseService;
import com.noize.shared.FinanceMonth;
import com.noize.shared.Member;
import com.noize.shared.MemberToFinances;
import com.noize.shared.MemberToTraining;
import com.noize.shared.Training;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server-side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class DatabaseServiceImpl extends RemoteServiceServlet implements
		DatabaseService {
	
	private static PersistenceManagerFactory PMF = JDOHelper.getPersistenceManagerFactory("transactions-optional");

	public Member addMember(String firstname, String lastname, String email,int role,String address,Date birthdate,int job) throws IllegalArgumentException {
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
		Member m = new Member(firstname,lastname,email,role,address,job);
		SimpleDateFormat sdate = new SimpleDateFormat("dd-MM-yyyy"); 
		String res = sdate.format(birthdate);
		m.setBirthdate(res);
		try {
			pm.makePersistent(m);
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
			pm.currentTransaction().begin();
			for(Long id : ids){
				Query v = pm.newQuery(MemberToTraining.class, "mid == " + id);
				v.deletePersistentAll();
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
	public List<Member> getMembers() {
		List<Member> list = new ArrayList<>();
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

	@Override
	public boolean addTraining(Date d) {
		SimpleDateFormat sdate = new SimpleDateFormat("dd-MM-yyyy"); 
		String res = sdate.format(d);
		Training t = new Training(res);
		PersistenceManager pm = getPersistenceManager();
		try{
			pm.currentTransaction().begin();
			pm.makePersistent(t);
			pm.currentTransaction().commit();
		}
		finally{
			pm.close();
		}
		return true;
	}

	@Override
	public List<Training> getTrainingAll() {
		List<Training> list = new ArrayList<Training>();
		PersistenceManager pm = getPersistenceManager();
		Extent<Training> e = pm.getExtent(Training.class);
		try {
			Iterator<Training> iter = e.iterator();
			while(iter.hasNext()){
				list.add((Training) iter.next());
			}
		}
		finally {
			e.closeAll();
			pm.close();
		}
		return list;
	}

	@Override
	public Training getTraining(String id) {
		List<Training> tlist;
		PersistenceManager pm = getPersistenceManager();
		pm.currentTransaction().begin();
		try{
			Query q = pm.newQuery(Training.class, "id == " + id);
			tlist = (List<Training>) q.execute();
			pm.currentTransaction().commit();
		}
		finally{
			pm.close();
		}
		return tlist.get(0);
	}

	@Override
	public void addMemberToTraining(MemberToTraining mtt) {
		PersistenceManager pm = getPersistenceManager();
		pm.currentTransaction().begin();
		try{
			pm.makePersistent(mtt);
			pm.currentTransaction().commit();
		}
		finally{
			pm.close();
		}
		
	}

	@Override
	public List<MemberToTraining> getMemberToTrainingAll() {
		List<MemberToTraining> list = new ArrayList<MemberToTraining>();
		PersistenceManager pm = getPersistenceManager();
		Extent<MemberToTraining> e = pm.getExtent(MemberToTraining.class);
		try {
			Iterator<MemberToTraining> iter = e.iterator();
			while(iter.hasNext()){
				list.add((MemberToTraining) iter.next());
			}
		}
		finally {
			e.closeAll();
			pm.close();
		}
		return list;
	}

	@Override
	public void deleteMemberToTraining(Long id) {
		PersistenceManager pm = getPersistenceManager();
		pm.currentTransaction().begin();
		try{
			Query q = pm.newQuery(MemberToTraining.class, "id == " + id);
			q.deletePersistentAll();
			pm.currentTransaction().commit();
		}
		finally{
			pm.close();
		}
	}

	@Override
	public void deleteTraining(Long id) {
		PersistenceManager pm = getPersistenceManager();
		pm.currentTransaction().begin();
		try{
			Query q = pm.newQuery(Training.class, "id == " + id);
			q.deletePersistentAll();
			pm.currentTransaction().commit();
			pm.currentTransaction().begin();
			Query v = pm.newQuery(MemberToTraining.class,"tid == " + id);
			v.deletePersistentAll();
			pm.currentTransaction().commit();
		}
		finally{
			pm.close();
		}
	}

	@Override
	public List<MemberToFinances> getMemberToFinancesAll() {
		List<MemberToFinances> list = new ArrayList<>();
		PersistenceManager pm = getPersistenceManager();
		Extent<MemberToFinances> e = pm.getExtent(MemberToFinances.class);
		try {
			Iterator<MemberToFinances> iter = e.iterator();
			while(iter.hasNext()){
				list.add((MemberToFinances) iter.next());
			}
		}
		finally {
			e.closeAll();
			pm.close();
		}
		return list;
	}

	@Override
	public void addMemberToFinances(MemberToFinances mtf) {
		PersistenceManager pm = getPersistenceManager();
		pm.currentTransaction().begin();
		try{
			pm.makePersistent(mtf);
			pm.currentTransaction().commit();
		}
		finally{
			pm.close();
		}
		
	}

	@Override
	public void deleteMemberToFinances(Long id) {
		PersistenceManager pm = getPersistenceManager();
		pm.currentTransaction().begin();
		try{
			Query q = pm.newQuery(MemberToFinances.class, "id == " + id);
			q.deletePersistentAll();
			pm.currentTransaction().commit();
		}
		finally{
			pm.close();
		}
		
	}

	@Override
	public void addNewFinancesYear(int year) {
		FinanceMonth[] months = new FinanceMonth[12];
		months[0] = new FinanceMonth(1,year);
		months[1] = new FinanceMonth(2,year);
		months[2] = new FinanceMonth(3,year);
		months[3]= new FinanceMonth(4,year);
		months[4] = new FinanceMonth(5,year);
		months[5] = new FinanceMonth(6,year);
		months[6] = new FinanceMonth(7,year);
		months[7]= new FinanceMonth(8,year);
		months[8] = new FinanceMonth(9,year);
		months[9] = new FinanceMonth(10,year);
		months[10] = new FinanceMonth(11,year);
		months[11] = new FinanceMonth(12,year);
//		try{
			PersistenceManager pm = getPersistenceManager();
//			pm.currentTransaction().begin();
			pm.makePersistentAll(months);	//,feb,mar,apr,mai,jun,jul,aug,sep,okt,nov,dec);
//			pm.currentTransaction().commit();
			pm.close();
//		}
//		finally{
//			pm.close();
//		}
	}

	@Override
	public List<FinanceMonth> getFinancesMonthAll() {
		List<FinanceMonth> list = new ArrayList<>();
		PersistenceManager pm = getPersistenceManager();
		Extent<FinanceMonth> e = pm.getExtent(FinanceMonth.class);
		try {
			Iterator<FinanceMonth> iter = e.iterator();
			while(iter.hasNext()){
				list.add((FinanceMonth) iter.next());
			}
		}
		finally {
			e.closeAll();
			pm.close();
		}
		return list;
	}
	
}
