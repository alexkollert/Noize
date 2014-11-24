package com.noize.client.presenter;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;
import com.noize.client.DatabaseServiceAsync;
import com.noize.client.events.MemberUpdatedEvent;
import com.noize.shared.Member;

public class EditMemberPresenter implements Presenter {
	
	private final DatabaseServiceAsync rpcService;
	private final HandlerManager eventbus;
	private final Display display;
	private Member member;
	
	public interface Display {
		HasClickHandlers getSaveButton();
		HasClickHandlers getCancelButton();
		HasValue<String> getFirstName();
		HasValue<String> getLastName();
		Widget asWidget();
	}
	
	public EditMemberPresenter(DatabaseServiceAsync rpcService,HandlerManager eventbus,Display view) {
		this.rpcService = rpcService;
		this.eventbus = eventbus;
		this.display = view;
//		this.member = new Member();
		bind();
	}

	private void bind() {
		display.getSaveButton().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				doSave();
				
			}
		});
		display.getCancelButton().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				doCancel();
				
			}
		});
		
	}

	private void doCancel() {
		// TODO Auto-generated method stub
		
	}

	private void doSave() {
//		member = new Member(display.getFirstName().getValue(), display.getLastName().getValue());
		rpcService.addMember(display.getFirstName().getValue(), display.getLastName().getValue(), new AsyncCallback<Member>() {
			
			
			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Fehler beim Bearbeiten!");
			}


			@Override
			public void onSuccess(Member result) {
				eventbus.fireEvent(new MemberUpdatedEvent());
				
			}
		});
		
	}

	@Override
	public void go(HasWidgets container) {
		container.clear();
		container.add(display.asWidget());

	}

}
