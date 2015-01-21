package com.noize.shared;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.gwt.user.client.rpc.IsSerializable;

@PersistenceCapable
public class FinanceMonth implements IsSerializable{

	@PrimaryKey
	@Persistent(valueStrategy=IdGeneratorStrategy.IDENTITY)
	private Long id;
	
	@Persistent
	private Integer month;
	
	@Persistent
	private Integer year;
	
	public FinanceMonth() {
	}
	
	public FinanceMonth(int month,int year){
		this.month = month;
		this.year = year;
	}
	
	public Long getId(){
		return this.id;
	}
	
	public Integer getName(){
		return this.month;
	}
	
	public Integer getYear(){
		return this.year;
	}
}
