package com.noize.client.events;

import com.google.gwt.event.shared.GwtEvent;

public class EditMemberEvent extends GwtEvent<EditMemberEventHandler>{
	
	public static Type<EditMemberEventHandler> TYPE = new Type<EditMemberEventHandler>();
	private Long id;

	public EditMemberEvent(Long id2) {
		this.id = id2;
	}

	@Override
	public Type<EditMemberEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(EditMemberEventHandler handler) {
		handler.onEditMember(id);
	}

}
