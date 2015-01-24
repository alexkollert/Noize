package com.noize.client.view;

import java.util.List;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.noize.client.presenter.FinancesPresenter.Display;
import com.noize.shared.FinanceYear;

public class FinancesView extends Composite implements Display{
	
	private FlexTable table;
	private ListBox yearPicker;
	private Button saveButton;
	private TextBox text;
	
	public FinancesView() {
		DecoratorPanel dp = new DecoratorPanel();
		initWidget(dp);
		
		table = new FlexTable();
		table.setBorderWidth(2);
		table.setCellPadding(2);
		
		VerticalPanel vp = new VerticalPanel();
		
		saveButton = new Button("Jahr anlegen");
		yearPicker = new ListBox();
		text = new TextBox();
		
		HorizontalPanel hp = new HorizontalPanel();
		hp.add(text);
		hp.add(saveButton);
		hp.add(yearPicker);
		
		vp.add(hp);
		vp.add(table);
		
		dp.add(vp);
	}

	@Override
	public Widget asWidget() {
		return this;
	}

	@Override
	public FlexTable getTable() {
		return this.table;
	}

	@Override
	public void setYearPicker(List<FinanceYear> years) {
		for(FinanceYear y : years){
			yearPicker.addItem(String.valueOf(y.getYear()));
		}
	}

	@Override
	public HasClickHandlers getButton() {
		return this.saveButton;
	}

	@Override
	public ListBox getYearPicker() {
		return this.yearPicker;
	}

	@Override
	public HasValue<String> getTextBox() {
		return this.text;
	}

}
