package com.noize.client.view;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.DateTimeFormat.PredefinedFormat;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;
import com.noize.client.presenter.EditMemberPresenter.Display;

public class EditMemberView extends Composite implements Display{
	
	
	private final TextBox firstName;
	private final TextBox lastName;
	private final TextBox email;
	private final TextBox address;
	private final DateBox birthdate;
	private final Button saveButton;
	private final Button cancelButton;
	private final ListBox roles;
	private final ListBox jobs;
	
	public EditMemberView() {
		DecoratorPanel dp = new DecoratorPanel();
		initWidget(dp);
		
		firstName = new TextBox();
		lastName = new TextBox();
		email = new TextBox();
		address = new TextBox();
		DateTimeFormat format = DateTimeFormat.getFormat(PredefinedFormat.YEAR_MONTH_NUM_DAY);
		birthdate = new DateBox();
		birthdate.setFormat(new DateBox.DefaultFormat(format));
		birthdate.getDatePicker().setYearAndMonthDropdownVisible(true);
		birthdate.getDatePicker().setVisibleYearCount(180);
		saveButton = new Button("Speichern");
		cancelButton = new Button("Abbrechen");
		roles = new ListBox();
		roles.addItem("Interessent");
		roles.addItem("Mitglied");
		roles.addItem("Ex-Mitglied");
		roles.addItem("Blockiert");
		jobs = new ListBox();
		jobs.addItem("Standard");
		jobs.addItem("Schüler/Student");
		jobs.addItem("Senior");
		
		VerticalPanel vpanel = new VerticalPanel();
		
		HorizontalPanel menupanel = new HorizontalPanel();
		menupanel.add(saveButton);
		menupanel.add(cancelButton);
		
		FlexTable detailstable = new FlexTable();
		detailstable.setWidget(0, 0, new Label("Status"));
		detailstable.setWidget(0, 1, roles);
		detailstable.setWidget(1, 0, new Label("Mitgliedschaft"));
		detailstable.setWidget(1, 1, jobs);
		detailstable.setWidget(2, 0, new Label("Vorname"));
		detailstable.setWidget(2, 1, firstName);
		detailstable.setWidget(3, 0, new Label("Nachname"));
		detailstable.setWidget(3, 1, lastName);
		detailstable.setWidget(4, 0, new Label("Adresse"));
		detailstable.setWidget(4, 1, address);
		detailstable.setWidget(5, 0, new Label("Email"));
		detailstable.setWidget(5, 1, email);
		detailstable.setWidget(6, 0, new Label("Geburtsdatum"));
		detailstable.setWidget(6, 1, birthdate);
		
		vpanel.add(detailstable);
		vpanel.add(menupanel);
		
		dp.add(vpanel);
	}
	
	@Override
	public HasClickHandlers getSaveButton() {
		return saveButton;
	}

	@Override
	public HasClickHandlers getCancelButton() {
		return cancelButton;
	}
	
	public Widget asWidget() {
		return this;
	}

	@Override
	public HasValue<String> getFirstName() {
		return firstName;
	}

	@Override
	public HasValue<String> getLastName() {
		return lastName;
	}

	@Override
	public HasValue<String> getEmail() {
		return email;
	}

	@Override
	public ListBox getRolePicker() {
		return roles;
	}

	@Override
	public HasValue<String> getAddress() {
		return this.address;
	}

	@Override
	public DateBox getBirthdate() {
		return this.birthdate;
	}

	@Override
	public ListBox getJobPicker() {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
