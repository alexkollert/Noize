package com.noize.client.presenter;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;
import com.noize.client.DatabaseServiceAsync;
import com.noize.shared.Member;

public class EditMemberPresenter implements Presenter {
	
	private final DatabaseServiceAsync rpcService;
	private final HandlerManager eventbus;
	private final Display display;
	private Member member;
	
	public interface Display {
		HasClickHandlers getSaveButton();
		HasClickHandlers getCancelButton();
		Widget asWidget();
	}
	
	public EditMemberPresenter(DatabaseServiceAsync rpcService,HandlerManager eventbus,Display view) {
		this.rpcService = rpcService;
		this.eventbus = eventbus;
		this.display = view;
		this.member = new Member();
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void go(HasWidgets container) {
		container.clear();
		container.add(display.asWidget());

	}

}
