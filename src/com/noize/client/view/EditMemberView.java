package com.noize.client.view;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.noize.client.presenter.EditMemberPresenter.Display;

public class EditMemberView extends Composite implements Display{
	
	private final TextBox firstName;
	private final TextBox lastName;
	private final Button saveButton;
	private final Button cancelButton;
	
	public EditMemberView() {
		DecoratorPanel dp = new DecoratorPanel();
		initWidget(dp);
		
		firstName = new TextBox();
		lastName = new TextBox();
		
		HorizontalPanel hpanel = new HorizontalPanel();
		saveButton = new Button("Speichern");
		cancelButton = new Button("Abbrechen");
		hpanel.add(firstName);
		hpanel.add(lastName);
		hpanel.add(saveButton);
		hpanel.add(cancelButton);
		
		dp.add(hpanel);
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

}
