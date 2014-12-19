package com.noize.client;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.RootPanel;
import com.noize.client.events.AddMemberEvent;
import com.noize.client.events.AddMemberEventHandler;
import com.noize.client.events.AppBusyEvent;
import com.noize.client.events.AppBusyEventHandler;
import com.noize.client.events.AppFreeEvent;
import com.noize.client.events.AppFreeEventHandler;
import com.noize.client.events.DeleteMemberEvent;
import com.noize.client.events.DeleteMemberEventHandler;
import com.noize.client.events.EditMemberEvent;
import com.noize.client.events.EditMemberEventHandler;
import com.noize.client.events.MemberUpdatedEvent;
import com.noize.client.events.MemberUpdatedEventHandler;
import com.noize.client.presenter.AttendancePresenter;
import com.noize.client.presenter.EditMemberPresenter;
import com.noize.client.presenter.MembersPresenter;
import com.noize.client.presenter.Presenter;
import com.noize.client.view.AttendanceView;
import com.noize.client.view.BusyIndicatorView;
import com.noize.client.view.EditMemberView;
import com.noize.client.view.MembersView;
import com.noize.client.view.NavBarView;

/**
 * This Controller manages History and Events
 *
 */
public class AppController implements Presenter,ValueChangeHandler<String> {
	private final DatabaseServiceAsync rpcService;
	private final HandlerManager eventbus;
	private HasWidgets mainContainer;
	private HasWidgets navContainer;
	
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
			else if (token.equals("presence")) {
				presenter = new AttendancePresenter(rpcService, eventbus, new AttendanceView());
			}
			if(presenter != null){
				presenter.go(mainContainer);
			}
		}
	}
	
	public void go(final HasWidgets mainContainer) {
		this.mainContainer = mainContainer;
		this.navContainer = RootPanel.get("nav");
		NavBarView navbar = new NavBarView();
		navContainer.add(navbar);
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
		eventbus.addHandler(EditMemberEvent.TYPE, new EditMemberEventHandler() {
			
			@Override
			public void onEditMember(Long id) {
				doEditMember(id);
			}
		});
		eventbus.addHandler(AppBusyEvent.TYPE, new AppBusyEventHandler() {
			
			@Override
			public void onAppBusy() {
				BusyIndicatorView.busy();
			}
		});
		eventbus.addHandler(AppFreeEvent.TYPE, new AppFreeEventHandler() {
			
			@Override
			public void onAppfree() {
				BusyIndicatorView.free();
				
			}
		});
	}

	private void doEditMember(Long id) {
		History.newItem("edit", false);
		Presenter presenter = new EditMemberPresenter(rpcService, eventbus, new EditMemberView(),id);
		presenter.go(mainContainer);
	}

	private void doMemberUpdated() {
		History.newItem("list");
	}

	private void doDeleteMember() {
		History.newItem("list");
	}

	private void doAddMember() {
		History.newItem("add");
	}

}
