package com.noize.client.presenter;

import java.util.Date;
import java.util.List;

import com.google.appengine.api.search.query.ExpressionParser.condExpr_return;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;
import com.noize.client.DatabaseServiceAsync;
import com.noize.client.events.AppBusyEvent;
import com.noize.client.events.AppFreeEvent;
import com.noize.shared.Member;
import com.noize.shared.Training;
import com.sun.xml.internal.ws.api.server.Container;

public class AttendancePresenter implements Presenter{
	private List<Member> members;
	private List<Training> days;
	private int rows = 0,cols = 0;
	
	private final DatabaseServiceAsync rpcService;
	private final HandlerManager eventbus;
	private final Display display;
	
	public interface Display {
		HasClickHandlers getSaveButton();
		DateBox getDateBox();
		void setDays(List<Training> list);
		void setMembers(List<Member> members);
		FlexTable getTable();
		Widget asWidget();
	}
	
	public AttendancePresenter(DatabaseServiceAsync rpcService,HandlerManager eventbus,Display view) {
		this.rpcService = rpcService;
		this.eventbus = eventbus;
		this.display = view;
		bind();
	}

	private void bind() {
		display.getSaveButton().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				Date date = display.getDateBox().getValue();
//				SimpleDateFormat d = new SimpleDateFormat("dd-MM-yyyy");
//				String result = d.format(date);
//				Date d = new SimpleDateFormat(pattern)
//				Training training = new Training(date);
				rpcService.addTraining(date, new AsyncCallback<Boolean>() {
					
					@Override
					public void onSuccess(Boolean result) {
//						History.fireCurrentHistoryState();
					}
					
					@Override
					public void onFailure(Throwable caught) {
						Window.alert("Termin konnte nicht erstellt werden");
					}
				});
			}
		});
	}

	@Override
	public void go(HasWidgets container) {
		container.clear();
		container.add(display.asWidget());
		fetchData();
	}

	private void fetchData() {
		eventbus.fireEvent(new AppBusyEvent());
		display.getTable().removeAllRows();
		display.getTable().setWidget(0, 0, new Label("Name"));
		rpcService.getTrainingAll(new AsyncCallback<List<Training>>() {
			
			@Override
			public void onSuccess(List<Training> result) {
				days = result;
//				ArrayList<String> list = new ArrayList<String>();
//				for(int i = 0;i < result.size();i++){
//					list.add(result.get(i).getDate());
//				}
//				days = list;
//				display.setDays(result);
//				ready = true;
				cols = result.size();
				for(int i = 0;i < result.size();i++){
					display.getTable().setWidget(0, i+1, new Label(result.get(i).getDate()));
				}
				fetchMembers();
			}
			
			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Fehler");
			}
		});
		eventbus.fireEvent(new AppFreeEvent());
	}

	private void fetchMembers() {
		rpcService.getMembers(new AsyncCallback<List<Member>>() {
			@Override
			public void onSuccess(List<Member> result) {
//				ArrayList<String> data = new ArrayList<String>();
//				for(int i = 0;i < members.size();i++){
//					data.add(result.get(i).getFirstName() + " " + result.get(i).getLastName());
//				}
				members = result;
//				display.setMembers(result);
				rows = result.size();
				for(int i = 0; i < result.size();i++){
					display.getTable().setWidget(i+1, 0, new Label(result.get(i).getFirstName() + " "+ result.get(i).getLastName()));
				}
				for(int i = 1;i <= rows;i++){
					for(int j = 1;j <= cols;j++){
						CheckBox checkbox = new CheckBox();
						checkbox.addClickHandler(new ClickHandler() {
							
							@Override
							public void onClick(ClickEvent event) {
								setDayinMember(event);
							}

						});
						checkbox.setName(String.valueOf(i)+"-"+String.valueOf(j));
						display.getTable().setWidget(i, j, checkbox);
					}
				}
			}
			
			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Fehler");
			}
		});
		
	}
	
	private void setDayinMember(ClickEvent event){
		int rowIndex = 0,colIndex = 0;
		CheckBox c = (CheckBox) event.getSource();
		String[] tmp = c.getName().split("-");
		rowIndex = Integer.valueOf(tmp[0]);
		colIndex = Integer.valueOf(tmp[1]);
		Member m = members.get(rowIndex-1);
//		Training t = days.get(colIndex - 1); //how to store the same Training from datastore into Member, this creates a new Training Record
		String tid = days.get(colIndex - 1).getID();
		if(c.getValue()){
			rpcService.storeDayinMember(m, tid, new AsyncCallback<Void>() {

				@Override
				public void onFailure(Throwable caught) {
					Window.alert("storeDayinMember Fehler");
				}

				@Override
				public void onSuccess(Void result) {
					Window.alert("storeDayinMember Erfolg");
				}
			});
		}
	}

}
