package com.noize.shared;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

import com.google.gwt.user.client.rpc.IsSerializable;


/**
 * This Entity is child of MemberSmall and contains all Information
 * about a Member. 
 *
 */
@PersistenceCapable
public class Member implements IsSerializable{
	
//	@PrimaryKey
//	@Persistent(valueStrategy=IdGeneratorStrategy.IDENTITY)
//	private Long id;
	
//	@Persistent
//	private MemberSmall smallmember;

	@Persistent
	private String firstName;
	
	@Persistent
	private String lastName;
	
	@Persistent
	private String email;
	
	
	public Member() {
		
	}
	
	public Member(String firstname, String lastname, String email){
		this.firstName = firstname;
		this.lastName = lastname;
		this.email = email;
	}
	
	public String getFirstName(){
		return this.firstName;
	}
	
	public String getLastName(){
		return this.lastName;
	}
	
//	public Long getId(){
//		return this.id;
//	}
	
	public void setFirstName(String str){
		this.firstName = str;
	}
	
	public String getEmail(){
		return this.email;
	}
	
//	public MemberSmall getMemberSmall(){
//		return this.smallmember;
//	}
//
//	public void setMemberSmall(MemberSmall m){
//		this.smallmember = m;
//	}
}
