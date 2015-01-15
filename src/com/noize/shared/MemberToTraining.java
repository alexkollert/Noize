package com.noize.shared;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

import com.google.gwt.user.client.rpc.IsSerializable;

@PersistenceCapable
public class MemberToTraining implements IsSerializable{
	
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
}
