package com.noize.client.view;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;

public class BusyIndicatorView extends Composite{
	
	static BusyIndicatorView busy;
	
	FlowPanel panel;
	
	public BusyIndicatorView() {
		panel = new FlowPanel();
		initWidget(panel);
		Label text = new Label("Lade...");
		panel.add(text);
		setVisible(false);
		RootPanel.get().add(this,Window.getClientWidth()-200,40);
	}
	
	public static void busy(){
		if(busy == null)
			busy = new BusyIndicatorView();
		busy.setVisible(true);
	}
	
	public static void free(){
		if(busy == null)
			busy = new BusyIndicatorView();
		busy.setVisible(false);
	}

	@Override
	public Widget asWidget() {
		return this;
	}
}
