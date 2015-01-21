package com.noize.client.view;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.noize.client.presenter.FinancesPresenter.Display;
import com.noize.shared.FinanceMonth;

public class FinancesView extends Composite implements Display{
	
	private FlexTable table;
	private ListBox yearPicker;
	private Button button;
	
	public FinancesView() {
		DecoratorPanel dp = new DecoratorPanel();
		initWidget(dp);
		
		table = new FlexTable();
		table.setBorderWidth(2);
		table.setCellPadding(2);
		
		VerticalPanel vp = new VerticalPanel();
		
		button = new Button("Neues Jahr anlegen");
		yearPicker = new ListBox();
		
		HorizontalPanel hp = new HorizontalPanel();
		hp.add(button);
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
	public void setYearPicker(List<FinanceMonth> months) {
		List<Integer> years = new ArrayList<Integer>();
		for(FinanceMonth fm : months){
			if(! years.contains(fm.getYear())){
				years.add(fm.getYear());
				yearPicker.addItem(fm.getYear().toString());
			}
		}
		
	}

	@Override
	public HasClickHandlers getButton() {
		return this.button;
	}

}
