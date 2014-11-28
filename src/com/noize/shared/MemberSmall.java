package com.noize.shared;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

import com.google.gwt.user.client.rpc.IsSerializable;


@PersistenceCapable
public class MemberSmall implements IsSerializable{
	
//	@PrimaryKey
//	@Persistent(valueStrategy=IdGeneratorStrategy.IDENTITY)
//	private Key key;
	
//	@Persistent
//	private Member fullMember;
	
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
	
	public String getDisplayName(){
		return this.displayName;
	}
	
//	public Member getFullMember(){
//		return this.fullMember;
//	}
//	
//	public void setFullMember(Member m){
//		this.fullMember = m;
//	}

}
