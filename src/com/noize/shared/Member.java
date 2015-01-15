package com.noize.shared;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 *
 */
@PersistenceCapable(identityType=IdentityType.APPLICATION)
public class Member implements IsSerializable{
	
	@PrimaryKey
	@Persistent(valueStrategy=IdGeneratorStrategy.IDENTITY)
	private Long id;
	
	@Persistent
	private String role;

	@Persistent
	private String firstName;
	
	@Persistent
	private String lastName;
	
	@Persistent
	private String email;
	
//	@Persistent
//	private List<Training> trainingDay = new ArrayList<Training>();
	
	public Member() {
		
	}
	
	public Member(String firstname, String lastname, String email,String role){
		this.firstName = firstname;
		this.lastName = lastname;
		this.email = email;
		this.role = role;
	}
	
	public String getFirstName(){
		return this.firstName;
	}
	
	public String getLastName(){
		return this.lastName;
	}
	
	public Long getId(){
		return this.id;
	}
	
	public void setFirstName(String str){
		this.firstName = str;
	}
	
	public void setLastName(String str){
		this.lastName = str;
	}
	
	public void setEmail(String str){
		this.email = str;
	}
	
	public void setRole(String str){
		this.role = str;
	}
	
	public String getEmail(){
		return this.email;
	}
	
//	public void addDay(Training date){
//		trainingDay.add(date);
//	}
//	
//	public List<Training> getTrainingDay(){
//		return this.trainingDay;
//	}
//	
//	public void setTrainingDay(List<Training> list){
//		this.trainingDay = list;
//	}
	
//	public boolean contains(Training t){
//		for(Training tmp : trainingDay){
//			if(tmp.getID().equals(t.getID()))
//				return true;
//		}
//		return false;
//	}
	
//	public MemberSmall getMemberSmall(){
//		return this.smallmember;
//	}
//
//	public void setMemberSmall(MemberSmall m){
//		this.smallmember = m;
//	}
}
