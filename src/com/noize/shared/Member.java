package com.noize.shared;

import java.util.Date;

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
	private int role;

	@Persistent
	private String firstName;
	
	@Persistent
	private String lastName;
	
	@Persistent
	private String email;
	
	@Persistent
	private String adress;
	
	@Persistent
	private String birthdate;
	
	public Member(){}
	
	public Member(String firstname, String lastname, String email,int role,String address){
		this.firstName = firstname;
		this.lastName = lastname;
		this.email = email;
		this.role = role;
		this.adress = address;
		this.birthdate = birthdate.toString();
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
	
	public void setAddress(String address){
		this.adress = address;
	}
	
	public void setBirthdate(String birthdate){
		this.birthdate = birthdate;
	}
	
	public void setRole(int val){
		this.role = val;
	}
	
	public String getEmail(){
		return this.email;
	}
	
	public int getRole(){
		return this.role;
	}
	
	public String getAddress(){
		return this.adress;
	}
	
	public String getBirthDate(){
		return this.birthdate;
	}
	
}
