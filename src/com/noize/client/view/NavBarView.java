package com.noize.client.view;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.Widget;

public class NavBarView extends Composite {

	private MenuBar navbar;
	
	public NavBarView(){
		navbar = new MenuBar();
		navbar.addItem("Start", new Command() {
			
			@Override
			public void execute() {
				History.newItem("list");
			}
		});
		navbar.addItem("Anwesenheit", new Command() {
			
			@Override
			public void execute() {
				History.newItem("presence");
			}
		});
		navbar.addItem("Finanzen", new Command() {
			
			@Override
			public void execute() {
				History.newItem("finances");
			}
		});
		initWidget(navbar);
	}
	
	@Override
	public Widget asWidget() {
		return this;
	}
	
}
