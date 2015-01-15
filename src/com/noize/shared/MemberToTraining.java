package com.noize.shared;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.gwt.user.client.rpc.IsSerializable;

@PersistenceCapable
public class MemberToTraining implements IsSerializable{
	
	@PrimaryKey
	@Persistent(valueStrategy=IdGeneratorStrategy.IDENTITY)
	private Long id;
	
	@Persistent
	private Long tid;
	
	@Persistent
	private Long mid;
	
	public MemberToTraining() {
	}

	public MemberToTraining(Long mid,Long tid){
		this.tid = tid;
		this.mid = mid;
	}
	
	public Long getTid(){
		return this.tid;
	}
	
	public Long getMid(){
		return this.mid;
	}
	
	public Long getId(){
		return this.id;
	}
}
