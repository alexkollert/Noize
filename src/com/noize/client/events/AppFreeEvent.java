package com.noize.client.events;

import com.google.gwt.event.shared.GwtEvent;

public class AppFreeEvent extends GwtEvent<AppFreeEventHandler>{
	
	public static Type<AppFreeEventHandler> TYPE = new Type<>();

	@Override
	public Type<AppFreeEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(AppFreeEventHandler handler) {
		handler.onAppfree();
		
	}

}
