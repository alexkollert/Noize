package com.noize.client.presenter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Widget;
import com.noize.client.DatabaseServiceAsync;
import com.noize.shared.FinanceMonth;
import com.noize.shared.FinanceYear;
import com.noize.shared.Member;
import com.noize.shared.MemberToFinances;

public class FinancesPresenter implements Presenter {

	private final DatabaseServiceAsync rpcService;
	private final HandlerManager eventbus;
	private final Display display;
	
	private List<Member> members;
	private List<MemberToFinances> mtf;
	private List<FinanceMonth> months;
	List<FinanceMonth> currentMonths;
	private int rows = 0, cols = 12;

	public interface Display {
		FlexTable getTable();
		void setYearPicker(List<FinanceYear> months);
		ListBox getYearPicker();
		HasClickHandlers getButton();
		HasValue<String> getTextBox();
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
				String val = display.getTextBox().getValue();
				if(val != null){
					Integer year = Integer.parseInt(val); //FIXME Error Handling
					rpcService.addNewFinancesYear(year, new AsyncCallback<Void>() {
						
						@Override
						public void onSuccess(Void result) {
							History.fireCurrentHistoryState();
						}
						
						@Override
						public void onFailure(Throwable caught) {
							Window.alert("Jahr konnte nicht angelegt werden");
							
						}
					});
				}
				else{
					History.fireCurrentHistoryState();
				}
				
			}
		});
		display.getYearPicker().addChangeHandler(new ChangeHandler() {
			
			@Override
			public void onChange(ChangeEvent event) {
				fetchData();
			}
		});
	}

	@Override
	public void go(HasWidgets container) {
		container.clear();
		container.add(display.asWidget());
		fetchYear();
	}
	
	public void fetchYear(){
		rpcService.getFinanceYears(new AsyncCallback<List<FinanceYear>>() {
			
			@Override
			public void onSuccess(List<FinanceYear> result) {
				List<FinanceYear> list = new ArrayList<FinanceYear>();
				list = result;
				display.setYearPicker(list);
				fetchData();
			}
			
			@Override
			public void onFailure(Throwable caught) {
				Window.alert("getFinanceYears: Daten konnten nicht geladen werden");
				
			}
		});
	}

	private void fetchData() {
		display.getTable().removeAllRows();
		display.getTable().setWidget(0, 0, new Label("Name"));
		ListBox yearPicker = display.getYearPicker();
		if(yearPicker.getItemCount() > 0){
			rpcService.getFinancesMonths(new AsyncCallback<List<FinanceMonth>>() {
				
				@Override
				public void onSuccess(List<FinanceMonth> result) {
					months = result;
//					cols = result.size();
					fetchMembers();
				}
				
				@Override
				public void onFailure(Throwable caught) {
					Window.alert("getFinancesMonths: Daten konnten nicht geladen werden");
				}
			});
		}
			
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
		rpcService.getMemberToFinances(new AsyncCallback<List<MemberToFinances>>() {
			
			@Override
			public void onSuccess(List<MemberToFinances> result) {
				mtf = result;
				createTable();
			}
			
			@Override
			public void onFailure(Throwable caught) {
				Window.alert("getMemverToFinances: Daten konnten nicht geladen werden");
			}
		});
	}

	private void createTable() {
		//Extract months only for current Year - should be done in DatabaseServiceImpl
		currentMonths = new ArrayList<FinanceMonth>();
		String res = display.getYearPicker().getValue(display.getYearPicker().getSelectedIndex());
		int year = Integer.parseInt(res);
		for(FinanceMonth fm : months){
			if(fm.getYear() == year)
				currentMonths.add(fm);
		}
		Collections.sort(currentMonths, new Comparator<FinanceMonth>() {

			@Override
			public int compare(FinanceMonth o1, FinanceMonth o2) {
				return o1.getName().compareTo(o2.getName());
			}
		});
		for (int i = 0; i < rows; i++) {
			display.getTable().setWidget(
					i + 1,
					0,
					new Label(members.get(i).getFirstName() + " "
							+ members.get(i).getLastName()));
		}
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
			for (int i = 1; i <= rows; i++) {
				for (int j = 1; j <= cols; j++) {
					CheckBox checkbox = new CheckBox();
					checkbox.addClickHandler(new ClickHandler() {

						@Override
						public void onClick(ClickEvent event) {
							setFinanceMonthMember(event);
						}

					});
					checkbox.setName(String.valueOf(i) + "-"
							+ String.valueOf(j));
					Member m = members.get(i - 1);
					FinanceMonth fm = currentMonths.get(j - 1);
					for (int k = 0; k < mtf.size(); k++) { //FIXME
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

	private void setFinanceMonthMember(ClickEvent event) {
		int rowIndex = 0, colIndex = 0;
		CheckBox c = (CheckBox) event.getSource();
		String[] tmp = c.getName().split("-");
		rowIndex = Integer.valueOf(tmp[0]);
		colIndex = Integer.valueOf(tmp[1]);
		Long mid = members.get(rowIndex - 1).getId();
		Long monthId = currentMonths.get(colIndex -1).getId();
		if (c.getValue()) {
			MemberToFinances mf = new MemberToFinances(mid, monthId);
			rpcService.addMemberToFinances(mf, new AsyncCallback<Void>() {
				
				@Override
				public void onSuccess(Void result) {
					// TODO Auto-generated method stub
				}
				
				@Override
				public void onFailure(Throwable caught) {
					Window.alert("Daten konnten nicht gespeichert werden");
				}
			});
		}
		else{
			for (int k = 0; k < mtf.size(); k++) {
				if (mtf.get(k).getMonthId().compareTo(monthId) == 0) {
					if (mtf.get(k).getMid().compareTo(mid) == 0) {
						rpcService.deleteMemberToFinances(mtf.get(k).getId(), new AsyncCallback<Void>() {
							
							@Override
							public void onSuccess(Void result) {
								// TODO Auto-generated method stub
							}
							
							@Override
							public void onFailure(Throwable caught) {
								Window.alert("Es ist ein Fehler beim Löschen aufgetreten");
							}
						});
					}
				}
			}
		}
	}
}
