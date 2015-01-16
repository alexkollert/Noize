package com.noize.shared;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.IsSerializable;


@PersistenceCapable(identityType=IdentityType.APPLICATION)
public class Training implements IsSerializable{
	
	@PrimaryKey
	@Persistent(valueStrategy=IdGeneratorStrategy.IDENTITY)
//	@Extension(vendorName="datanucleus",key="gae.encoded-pk",value="true")
	private Long id;

	@Persistent
	private String date;
	
	public Training() {
		
	}
	
	public Training(String date){
		this.date = date;
	}
	
	public void setDate(String date){
		this.date = date;
	}
	
	public String getDate(){
		return this.date;
	}
	
	public Long getID(){
		return this.id;
	}
	
}
