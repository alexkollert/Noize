package com.noize.shared;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.gwt.user.client.rpc.IsSerializable;

@PersistenceCapable
public class MemberToFinances implements IsSerializable{
	
	@PrimaryKey
	@Persistent(valueStrategy=IdGeneratorStrategy.IDENTITY)
	private Long id;

	@Persistent
	private Long mid;
	
	@Persistent
	private Long monthid;
	
	public MemberToFinances(){
		
	}
	
	public MemberToFinances(Long mid,Long monthid){
		this.mid = mid;
		this.monthid = monthid;
	}
	
	public Long getId(){
		return this.id;
	}
	
	public Long getMid(){
		return this.mid;
	}
	
	public Long getMonthId(){
		return this.monthid;
	}
}
