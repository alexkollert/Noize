package com.noize.client.events;

import com.google.gwt.event.shared.GwtEvent;

public class MemberUpdatedEvent extends GwtEvent<MemberUpdatedEventHandler>{
	
	public static Type<MemberUpdatedEventHandler> TYPE = new Type<MemberUpdatedEventHandler>();

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<MemberUpdatedEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(MemberUpdatedEventHandler handler) {
		handler.onMemberUpdated();
		
	}

}
