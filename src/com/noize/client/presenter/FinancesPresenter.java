package com.noize.client.presenter;

import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.noize.client.DatabaseServiceAsync;
import com.noize.shared.FinanceMonth;
import com.noize.shared.Member;
import com.noize.shared.MemberToFinances;
import com.noize.shared.Training;

public class FinancesPresenter implements Presenter {

	private final DatabaseServiceAsync rpcService;
	private final HandlerManager eventbus;
	private final Display display;
	
	private List<Member> members;
	private List<MemberToFinances> mtf;
	private List<FinanceMonth> months;
	private int rows = 0, cols = 0;

	public interface Display {
		FlexTable getTable();
		void setYearPicker(List<FinanceMonth> months);
		HasClickHandlers getButton();
		Widget asWidget();
	}

	public FinancesPresenter(DatabaseServiceAsync rpcService,
			HandlerManager eventbus, Display view) {
		this.rpcService = rpcService;
		this.eventbus = eventbus;
		this.display = view;
		bind();
	}

	private void bind() {
		display.getButton().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				String val = Window.prompt("Jahr eingeben", "");
				int year = Integer.parseInt(val);
				if(val != null){
					rpcService.addNewFinancesYear(year, new AsyncCallback<Void>() {
						
						@Override
						public void onSuccess(Void result) {
							// TODO Auto-generated method stub
							
						}
						
						@Override
						public void onFailure(Throwable caught) {
							Window.alert("Jahr konnte nicht angelegt werden");
							
						}
					});
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
		display.getTable().removeAllRows();
		display.getTable().setWidget(0, 0, new Label("Name"));
		rpcService.getFinancesMonthAll(new AsyncCallback<List<FinanceMonth>>() {
			
			@Override
			public void onSuccess(List<FinanceMonth> result) {
				months = result;
				cols = result.size();
				display.setYearPicker(months);
				fetchMembers();
			}
			
			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Daten konnten nicht geladen werden");
			}
		});
		
	}

	private void fetchMembers() {
		rpcService.getMembers(new AsyncCallback<List<Member>>() {
			
			@Override
			public void onSuccess(List<Member> result) {
				members = result;
				rows = result.size();
				fetchRelations();
			}
			
			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Daten konnten nicht geladen werden");
			}
		});
		
	}

	private void fetchRelations() {
		rpcService.getMemberToFinancesAll(new AsyncCallback<List<MemberToFinances>>() {
			
			@Override
			public void onSuccess(List<MemberToFinances> result) {
				mtf = result;
				createTable();
			}
			
			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Daten konnten nicht geladen werden");
			}
		});
	}

	private void createTable() {
		display.getTable().setWidget(0, 1,new Label("  Januar "));
		display.getTable().setWidget(0, 2,new Label(" Februar "));
		display.getTable().setWidget(0, 3,new Label("   März  "));
		display.getTable().setWidget(0, 4,new Label("  April  "));
		display.getTable().setWidget(0, 5,new Label("   Mai   "));
		display.getTable().setWidget(0, 6,new Label("   Juni  "));
		display.getTable().setWidget(0, 7,new Label("  Juli   "));
		display.getTable().setWidget(0, 8,new Label("  August "));
		display.getTable().setWidget(0, 9,new Label("September"));
		display.getTable().setWidget(0, 10,new Label(" Oktober "));
		display.getTable().setWidget(0, 11,new Label(" November"));
		display.getTable().setWidget(0, 12,new Label(" Dezember"));
		for (int i = 0; i < members.size(); i++) {
			display.getTable().setWidget(
					i + 1,
					0,
					new Label(members.get(i).getFirstName() + " "
							+ members.get(i).getLastName()));
		}
		for (int i = 1; i <= rows; i++) {
			for (int j = 1; j <= cols; j++) {
				CheckBox checkbox = new CheckBox();
				checkbox.addClickHandler(new ClickHandler() {

					@Override
					public void onClick(ClickEvent event) {
//						setDayinMember(event);
					}

				});
				checkbox.setName(String.valueOf(i) + "-"
						+ String.valueOf(j));
				Member m = members.get(i - 1);
//				Training t = days.get(j - 1);
				FinanceMonth fm = months.get(j - 1);
				for (int k = 0; k < mtf.size(); k++) {
					if (mtf.get(k).getMonthId().compareTo(fm.getId()) == 0) {
						if (mtf.get(k).getMid().compareTo(m.getId()) == 0) {
							checkbox.setValue(true);
						}
					}
				}
				display.getTable().setWidget(i, j, checkbox);
			}
		}
	}

}
