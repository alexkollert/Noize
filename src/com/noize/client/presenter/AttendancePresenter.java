package com.noize.client.presenter;

import java.util.Date;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.InvocationException;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTMLTable.Cell;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;
import com.noize.client.DatabaseServiceAsync;
import com.noize.client.events.AppBusyEvent;
import com.noize.client.events.AppFreeEvent;
import com.noize.shared.Member;
import com.noize.shared.MemberToTraining;
import com.noize.shared.Training;

public class AttendancePresenter implements Presenter {
	private List<Member> members;
	private List<Training> days;
	private List<MemberToTraining> mtt;
	private int rows = 0, cols = 0;

	static Member currentMember;

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

	public AttendancePresenter(DatabaseServiceAsync rpcService,
			HandlerManager eventbus, Display view) {
		this.rpcService = rpcService;
		this.eventbus = eventbus;
		this.display = view;
		eventbus.fireEvent(new AppBusyEvent());
		bind();
		eventbus.fireEvent(new AppFreeEvent());
	}

	private void bind() {
		display.getSaveButton().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				Date date = display.getDateBox().getValue();
				if(date != null){
					rpcService.addTraining(date, new AsyncCallback<Boolean>() {
	
						@Override
						public void onSuccess(Boolean result) {
							History.fireCurrentHistoryState();
						}
	
						@Override
						public void onFailure(Throwable caught) {
							 if (caught instanceof InvocationException) {
						            InvocationException ie = (InvocationException) caught;
						            if(ie.getMessage().contains("j_spring_security_check"))
						            {
						                Window.alert("Session is timed out. Please login again");
						                Window.open(GWT.getHostPageBaseURL() + "login.html", "_self", null);
						                return;
						            }
						      }
						}
					});
				}
			}
		});
		display.getTable().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				Cell src = display.getTable().getCellForEvent(event);
				if(src.getRowIndex() == 0 && src.getCellIndex() > 0 && src.getCellIndex() <= days.size()){
					boolean ok = Window.confirm("Datum löschen?");
					if(ok){
						rpcService.deleteTraining(days.get(src.getCellIndex() - 1).getID(), new AsyncCallback<Void>() {

							@Override
							public void onFailure(Throwable caught) {
								 if (caught instanceof InvocationException) {
							            InvocationException ie = (InvocationException) caught;
							            if(ie.getMessage().contains("j_spring_security_check"))
							            {
							                Window.alert("Session is timed out. Please login again");
							                Window.open(GWT.getHostPageBaseURL() + "login.jsp", "_self", null);
							                return;
							            }
							        }
								
							}

							@Override
							public void onSuccess(Void result) {
								// TODO Auto-generated method stub
								
							}
						});
					}
					else{
						History.fireCurrentHistoryState();
					}
						
				}
				
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
				cols = result.size();
				for (int i = 0; i < result.size(); i++) {
					display.getTable().setWidget(0, i + 1,
							new Label(result.get(i).getDate()));
				}
				fetchMemberToTraining();
			}

			@Override
			public void onFailure(Throwable caught) {
				 if (caught instanceof InvocationException) {
			            InvocationException ie = (InvocationException) caught;
			            if(ie.getMessage().contains("j_spring_security_check"))
			            {
			                Window.alert("Session is timed out. Please login again");
			                Window.open(GWT.getHostPageBaseURL() + "login.jsp", "_self", null);
			                return;
			            }
			        }
			}
		});
	}

	private void fetchMemberToTraining() {
		rpcService
				.getMemberToTrainingAll(new AsyncCallback<List<MemberToTraining>>() {

					@Override
					public void onSuccess(List<MemberToTraining> result) {
						mtt = result;
						fetchMembers();
					}

					@Override
					public void onFailure(Throwable caught) {
						 if (caught instanceof InvocationException) {
					            InvocationException ie = (InvocationException) caught;
					            if(ie.getMessage().contains("j_spring_security_check"))
					            {
					                Window.alert("Session is timed out. Please login again");
					                Window.open(GWT.getHostPageBaseURL() + "login.jsp", "_self", null);
					                return;
					            }
					        }
					}
				});

	}

	private void fetchMembers() {
		rpcService.getMembers(new AsyncCallback<List<Member>>() {
			@Override
			public void onSuccess(List<Member> result) {
				members = result;
				rows = result.size();
				for (int i = 0; i < result.size(); i++) {
					display.getTable().setWidget(
							i + 1,
							0,
							new Label(result.get(i).getFirstName() + " "
									+ result.get(i).getLastName()));
				}
				for (int i = 1; i <= rows; i++) {
					for (int j = 1; j <= cols; j++) {
						CheckBox checkbox = new CheckBox();
						checkbox.addClickHandler(new ClickHandler() {

							@Override
							public void onClick(ClickEvent event) {
								setDayinMember(event);
							}

						});
						checkbox.setName(String.valueOf(i) + "-"
								+ String.valueOf(j));
						Member m = members.get(i - 1);
						Training t = days.get(j - 1);
						for (int k = 0; k < mtt.size(); k++) {
							if (mtt.get(k).getTid().compareTo(t.getID()) == 0) {
								if (mtt.get(k).getMid().compareTo(m.getId()) == 0) {
									checkbox.setValue(true);
								}
							}
						}
						display.getTable().setWidget(i, j, checkbox);
					}
				}
				eventbus.fireEvent(new AppFreeEvent());
			}

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Fehler");
			}
		});

	}

	private void setDayinMember(ClickEvent event) {
		int rowIndex = 0, colIndex = 0;
		CheckBox c = (CheckBox) event.getSource();
		String[] tmp = c.getName().split("-");
		rowIndex = Integer.valueOf(tmp[0]);
		colIndex = Integer.valueOf(tmp[1]);
		Long mid = members.get(rowIndex - 1).getId();
		Long tid = days.get(colIndex - 1).getID();
		if (c.getValue()) {
			MemberToTraining mt = new MemberToTraining(mid, tid);
			rpcService.addMemberToTraining(mt, new AsyncCallback<Void>() {

				@Override
				public void onSuccess(Void result) {
					// TODO Auto-generated method stub
				}

				@Override
				public void onFailure(Throwable caught) {
					 if (caught instanceof InvocationException) {
				            InvocationException ie = (InvocationException) caught;
				            if(ie.getMessage().contains("j_spring_security_check"))
				            {
				                Window.alert("Session is timed out. Please login again");
				                Window.open(GWT.getHostPageBaseURL() + "login.jsp", "_self", null);
				                return;
				            }
				        }
				}
			});
		} else {
			for (int k = 0; k < mtt.size(); k++) {
				if (mtt.get(k).getTid().compareTo(tid) == 0) {
					if (mtt.get(k).getMid().compareTo(mid) == 0) {
						rpcService.deleteMemberToTraining(mtt.get(k).getId(),
								new AsyncCallback<Void>() {

									@Override
									public void onSuccess(Void result) {
										// TODO Auto-generated method stub
									}

									@Override
									public void onFailure(Throwable caught) {
										 if (caught instanceof InvocationException) {
									            InvocationException ie = (InvocationException) caught;
									            if(ie.getMessage().contains("j_spring_security_check"))
									            {
									                Window.alert("Session is timed out. Please login again");
									                Window.open(GWT.getHostPageBaseURL() + "login.jsp", "_self", null);
									                return;
									            }
									        }
									}
								});
					}
				}
			}
		}
	}

}
