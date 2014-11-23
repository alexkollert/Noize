package com.noize.client.events;

import com.google.gwt.event.shared.GwtEvent;

public class DeleteMemberEvent extends GwtEvent<DeleteMemberEventHandler>{
	
	public static Type<DeleteMemberEventHandler> TYPE = new Type<DeleteMemberEventHandler>();

	@Override
	public Type<DeleteMemberEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(DeleteMemberEventHandler handler) {
		handler.onDeleteMember(this);
	}

}
