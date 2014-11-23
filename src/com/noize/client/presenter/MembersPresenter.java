package com.noize.client.presenter;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;
import com.noize.client.DatabaseServiceAsync;
import com.noize.client.events.AddMemberEvent;
import com.noize.client.events.DeleteMemberEvent;
import com.noize.shared.Member;

public class MembersPresenter implements Presenter{
	
	private List<Member> members;
	
	private final DatabaseServiceAsync rpcService;
	private final HandlerManager eventbus;
	private final Display display;
	
	public interface Display {
		HasClickHandlers getDeleteButton();
		HasClickHandlers getAddButton();
		void setData(List<String> data);
		Widget asWidget();
	}
	
	public MembersPresenter(DatabaseServiceAsync rpcService, HandlerManager eventbus, Display view) {
		this.rpcService = rpcService;
		this.eventbus = eventbus;
		this.display = view;
	}

	private void bind() {
		display.getAddButton().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				eventbus.fireEvent(new AddMemberEvent());
				
			}
		});
		display.getDeleteButton().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				eventbus.fireEvent(new DeleteMemberEvent());
			}
		});
		
	}

	@Override
	public void go(HasWidgets container) {
		bind();
		container.clear();
		container.add(display.asWidget());
		fetchMembers();
	}

	private void fetchMembers() {
		rpcService.getMembers(new AsyncCallback<ArrayList<Member>>() {
			
			@Override
			public void onSuccess(ArrayList<Member> result) {
				members = result;
				ArrayList<String> data = new ArrayList<String>();
				for(int i = 0;i < data.size();i++){
					members.get(i).getFirstName();
				}
				display.setData(data);
			}
			
			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Server Error");
				
			}
		});
		
	}
	
	
	
}
