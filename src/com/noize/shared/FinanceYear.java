package com.noize.shared;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.gwt.user.client.rpc.IsSerializable;

@PersistenceCapable
public class FinanceYear implements IsSerializable{

	@PrimaryKey
	@Persistent(valueStrategy=IdGeneratorStrategy.IDENTITY)
	private Long id;
	
	@Persistent
	private int year;
	
	public FinanceYear(){}
	
	public FinanceYear(int year){
		this.year = year;
	}
	
	public int getYear(){
		return this.year;
	}
	
	public Long getId(){
		return this.id;
	}
}
