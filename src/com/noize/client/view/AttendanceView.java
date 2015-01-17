package com.noize.client.view;

import java.util.List;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.DateTimeFormat.PredefinedFormat;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;
import com.noize.client.presenter.AttendancePresenter.Display;
import com.noize.shared.Member;
import com.noize.shared.Training;

public class AttendanceView extends Composite implements Display{
	private final DateBox pickDay;
	private final Button saveDay;
	private FlexTable table;
	
	private int rows = 0,cols = 0;
	
	public AttendanceView(){
		DecoratorPanel dp = new DecoratorPanel();
		initWidget(dp);
		
		VerticalPanel vpanel = new VerticalPanel();
		
		HorizontalPanel hpanel = new HorizontalPanel();
		Label text = new Label();
		text.setText("Neuen Probetermin erstellen ");
		
		DateTimeFormat format = DateTimeFormat.getFormat(PredefinedFormat.YEAR_MONTH_NUM_DAY);
		pickDay = new DateBox();
		pickDay.setFormat(new DateBox.DefaultFormat(format));
		saveDay = new Button("Speichern");
		
		hpanel.add(text);
		hpanel.add(pickDay);
		hpanel.add(saveDay);
		
		table = new FlexTable();
		table.setBorderWidth(2);
		table.setCellPadding(2);
		
		vpanel.add(hpanel);
		vpanel.add(table);
		
		dp.add(vpanel);
	}
	
	@Override
	public Widget asWidget() {
		return this;
	}

	@Override
	public DateBox getDateBox() {
		return this.pickDay;
	}

	@Override
	public HasClickHandlers getSaveButton() {
		return this.saveDay;
	}

	@Override
	public void setDays(List<Training> days) {
		cols = days.size();
		GWT.log("Columns: "+cols);
		for(int i = 0;i < days.size();i++){
			Label tb = new Label(days.get(i).getDate());
			table.setWidget(0, i+1, tb);
		}
	}
	
	public void setMembers(List<Member> members){
		for(int i = 0; i < members.size();i++){
			table.setWidget(i+1, 0, new Label(members.get(i).getFirstName() + " "+ members.get(i).getLastName()));
		}
	}

//	private void fillTable() {
//		GWT.log("fillTabel called");
//		for(int i = 1;i < rows;i++){
//			for(int j = 1;j < cols;j++){
//				table.setWidget(i, j, new CheckBox());
//			}
//		}
//	}

	@Override
	public FlexTable getTable() {
		return this.table;
	}
	
//	public class DateLabel extends Composite implements HasClickHandlers{
//		
//		private Label date;
//		
//		public DateLabel(String date) {
//			this.date = new Label(date);
//		}
//		
//		@Override
//		public Widget asWidget() {
//			return this;
//		}
//
//		@Override
//		public HandlerRegistration addClickHandler(ClickHandler handler) {
//			return addHandler(handler, ClickEvent.getType());
//		}
//	}

}
