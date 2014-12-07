package com.noize.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Noize implements EntryPoint {

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		final DatabaseServiceAsync databaseService = GWT
				.create(DatabaseService.class);
		HandlerManager eventbus = new HandlerManager(null);
		AppController appViewer = new AppController(databaseService, eventbus);
		appViewer.go(RootPanel.get());
	}
}
