package com.noize.client.view;

import java.util.List;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.noize.client.presenter.MembersPresenter.Display;

public class MembersView extends Composite implements Display{
	private final Button addButton;
	private final Button deleteButton;
	private FlexTable contactsTable;
	private final FlexTable contentTable;
	
	public MembersView() {
		DecoratorPanel memListdeco = new DecoratorPanel();
		initWidget(memListdeco);
		
		contentTable = new FlexTable();
		
		HorizontalPanel hpanel = new HorizontalPanel();
		addButton = new Button("Neu");
		deleteButton = new Button("Löschen");
		hpanel.add(addButton);
		hpanel.add(deleteButton);
		contentTable.setWidget(0, 0, hpanel);
		
		contactsTable = new FlexTable();
		contactsTable.setWidth("100%");
//		contactsTable.addStyleName("contacts-ListContents");
		contentTable.setWidget(1, 0, contactsTable);
		
		memListdeco.add(contentTable);
	}
	
	public void setData(List<String> data) {
		contactsTable.removeAllRows();
		for(int i = 0;i < data.size();i++){
			contactsTable.setWidget(i, 0, new CheckBox());
			contactsTable.setText(i, 1, data.get(i));
		}
	}

	@Override
	public HasClickHandlers getDeleteButton() {
		return deleteButton;
	}

	@Override
	public HasClickHandlers getAddButton() {
		return addButton;
	}

	@Override
	public Widget asWidget() {
		return this;
	}
	

}
