package com.noize.client.view;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.noize.client.presenter.MembersPresenter.Display;

public class MembersView extends Composite implements Display{
	
	private final Button addButton;
	private final Button deleteButton;
	private final Button editButton;
	private FlexTable membersTable;
	private final FlexTable contentTable;
	
	public class MembersTableRow extends Composite {
//		private CheckBox checkbox;
		private Label text;
//		private Button edit;
		
		public MembersTableRow() {
			HorizontalPanel hp = new HorizontalPanel();
			initWidget(hp);
			
//			checkbox = new CheckBox();
			text = new Label();
//			edit = new Button("Bearbeiten");
			
//			hp.add(checkbox);
			hp.add(text);
//			hp.add(edit);
		}
		
		public void setText(String text){
			this.text.setText(text);
		}
		
		@Override
		public Widget asWidget() {
			return this;
		}
		
//		public HasClickHandlers getEditButton(){
//			return this.edit;
//		}

	}
	
	public MembersView() {
		
		DecoratorPanel memListdeco = new DecoratorPanel();
		initWidget(memListdeco);
//		memListdeco.setWidth("100%");
		
		contentTable = new FlexTable();
//		contentTable.setWidth("100%");
//		contentTable.getCellFormatter().setWidth(0, 0, "100%");
//		contentTable.getCellFormatter().setVerticalAlignment(0, 0, DockPanel.ALIGN_TOP);
		
		HorizontalPanel hpanel = new HorizontalPanel();
		addButton = new Button("Neu");
		deleteButton = new Button("Löschen");
		editButton = new Button("Bearbeiten");
		hpanel.add(addButton);
		hpanel.add(deleteButton);
		hpanel.add(editButton);
		contentTable.setWidget(0, 0, hpanel);
		
		membersTable = new FlexTable();
//		membersTable.addClickHandler(new TableMouseEvent(rpcService, eventbus,container));
//		contactsTable.setWidth("100%");
//		contactsTable.getColumnFormatter().setWidth(0, "15px");
//		contactsTable.addStyleName("contacts-ListContents");
		contentTable.setWidget(1, 0, membersTable);
		
		memListdeco.add(contentTable);
	}
	
	public void setData(List<String> data) {
		membersTable.removeAllRows();
		for(int i = 0;i < data.size();i++){
			MembersTableRow row = new MembersTableRow();
			row.setText(data.get(i));
			membersTable.setWidget(i, 0, new CheckBox());
			membersTable.setWidget(i, 1, row);
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

	@Override
	public List<Integer> getSelectedRows() {
		List<Integer> selectedRows = new ArrayList<Integer>();
		for(int i = 0;i < membersTable.getRowCount(); i++){
			CheckBox checkbox = (CheckBox) membersTable.getWidget(i, 0);
			if(checkbox.getValue()){
				selectedRows.add(i);
			}
		}
		return selectedRows;
	}

	@Override
	public HasClickHandlers getEditButton() {
		return editButton;
	}
	

}
