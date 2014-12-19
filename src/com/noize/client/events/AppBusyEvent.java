package com.noize.client.events;

import com.google.gwt.event.shared.GwtEvent;

public class AppBusyEvent extends GwtEvent<AppBusyEventHandler>{
	
	public static Type<AppBusyEventHandler> TYPE = new Type<>();

	@Override
	public GwtEvent.Type<AppBusyEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(AppBusyEventHandler handler) {
		handler.onAppBusy();
	}

}
