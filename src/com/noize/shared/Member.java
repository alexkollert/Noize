package com.noize.shared;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.gwt.user.client.rpc.IsSerializable;


@PersistenceCapable(identityType=IdentityType.APPLICATION)
public class Member implements IsSerializable{
	
	@PrimaryKey
	@Persistent(valueStrategy=IdGeneratorStrategy.IDENTITY)
	private Long id;
	
	@Persistent
	private String firstName;
	
	@Persistent
	private String lastName;
	
	public Member() {
		
	}
	
	public Member(String firstname, String lastname){
		this.firstName = firstname;
		this.lastName = lastname;
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

}
