package com.noize.client;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.HasWidgets;
import com.noize.client.events.AddMemberEvent;
import com.noize.client.events.AddMemberEventHandler;
import com.noize.client.events.DeleteMemberEvent;
import com.noize.client.events.DeleteMemberEventHandler;
import com.noize.client.events.MemberUpdatedEvent;
import com.noize.client.events.MemberUpdatedEventHandler;
import com.noize.client.presenter.EditMemberPresenter;
import com.noize.client.presenter.MembersPresenter;
import com.noize.client.presenter.Presenter;
import com.noize.client.view.EditMemberView;
import com.noize.client.view.MembersView;

public class AppController implements Presenter,ValueChangeHandler<String> {
	private final DatabaseServiceAsync rpcService;
	private final HandlerManager eventbus;
	private HasWidgets container;
	
	public AppController(DatabaseServiceAsync rpcService,HandlerManager eventbus) {
		this.rpcService = rpcService;
		this.eventbus = eventbus;
		bind();
	}
	

	@Override
	public void onValueChange(ValueChangeEvent<String> event) {
		String token = event.getValue();
		Presenter presenter = null;
		if(token != null){
			if(token.equals("list")){
				presenter = new MembersPresenter(rpcService,eventbus, new MembersView());
			}
			else if(token.equals("add")){
				presenter = new EditMemberPresenter(rpcService, eventbus, new EditMemberView());
			}
			else if(token.equals("edit")){
				presenter = new EditMemberPresenter(rpcService,eventbus,new EditMemberView());
			}
			
			
			if(presenter != null){
				presenter.go(container);
			}
				
		}
		
	}
	
	public void go(final HasWidgets container) {
		this.container = container;
		if ("".equals(History.getToken()))
			History.newItem("list");
		else
			History.fireCurrentHistoryState();		
	}
	
	private void bind(){
		History.addValueChangeHandler(this);
		
		eventbus.addHandler(AddMemberEvent.TYPE, new AddMemberEventHandler() {
			
			@Override
			public void onAddMember(AddMemberEvent e) {
				doAddMember();
			}
		});
		eventbus.addHandler(DeleteMemberEvent.TYPE, new DeleteMemberEventHandler() {
			
			@Override
			public void onDeleteMember(DeleteMemberEvent e) {
				doDeleteMember();	
			}
		});
		eventbus.addHandler(MemberUpdatedEvent.TYPE, new MemberUpdatedEventHandler() {
			
			@Override
			public void onMemberUpdated() {
				doMemberUpdated();
				
			}
		});
	}


	private void doMemberUpdated() {
		History.newItem("list");
		
	}


	protected void doDeleteMember() {
		History.newItem("list");
		
	}

	private void doAddMember() {
		History.newItem("add");
	}

}
