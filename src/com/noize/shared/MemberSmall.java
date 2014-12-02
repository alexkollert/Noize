package com.noize.shared;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.gwt.user.client.rpc.IsSerializable;


/**
 * This Entity contains a small subset of Information about a Member. 
 * The startpage loads this Entity as preview of the members. A Member
 * Enity with all Information is only loaded when requested.
 *
 */
@PersistenceCapable(identityType=IdentityType.APPLICATION)
public class MemberSmall implements IsSerializable{
	
	@PrimaryKey
	@Persistent(valueStrategy=IdGeneratorStrategy.IDENTITY)
	private Long id;
	
	@Persistent
	private Member fullMember;
	
	@Persistent
	private String displayName;
	
	public MemberSmall(){
		
	}
	
	public MemberSmall(String displayName){
		this.displayName = displayName;
	}
	
//	public Key getKey(){
//		return this.key;
//	}
	
	public Long getId(){
		return this.id;
	}
	
	public String getDisplayName(){
		return this.displayName;
	}
	
	public Member getFullMember(){
		return this.fullMember;
	}
	
	public void setFullMember(Member m){
		this.fullMember = m;
	}

}
