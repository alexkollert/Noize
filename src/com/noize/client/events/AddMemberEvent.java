package com.noize.client.events;

import com.google.gwt.event.shared.GwtEvent;

public class AddMemberEvent extends GwtEvent<AddMemberEventHandler>{
	
	public static Type<AddMemberEventHandler> TYPE = new Type<AddMemberEventHandler>();

	@Override
	public Type<AddMemberEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(AddMemberEventHandler handler) {
		handler.onAddMember(this);
		
	}

}
