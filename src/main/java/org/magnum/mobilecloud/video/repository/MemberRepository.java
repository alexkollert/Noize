package org.magnum.mobilecloud.video.repository;

import java.util.Collection;
import java.util.List;

import javax.jdo.Query;

import org.springframework.stereotype.Service;

@Service
public class MemberRepository extends JDOCrudRepository<Member, Long>{

	public MemberRepository() {
		super(Member.class);
	}
	
	@SuppressWarnings("unchecked")
	public Collection<Member> findByName(String name){
		Query query = PMF.get().getPersistenceManager().newQuery(Member.class);
		query.setFilter("name == n");
		query.declareParameters("String n");
		return (List<Member>)query.execute(name);
	}

}
