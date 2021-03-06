package com.contracteasy.client.session.page;

import com.contracteasy.client.communication.AccessServerCaller;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class LoginPage implements Page {
	
	TextBox username;
	TextBox password;

	@Override
	public void build(RootPanel root) {
		VerticalPanel panel = new VerticalPanel();
		
		HorizontalPanel uPanel = new HorizontalPanel();
		Label uLabel = new Label("Username");
		username = new TextBox();
		uPanel.add(uLabel);
		uPanel.add(username);
		
		HorizontalPanel pPanel = new HorizontalPanel();
		Label pLabel = new Label("Password");
		password = new PasswordTextBox();
		uPanel.add(pLabel);
		uPanel.add(password);
		
		Button login = new Button("Login");
		
		login.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				AccessServerCaller.getInstance().login(username.getText(), password.getText());				
			}
		});
		
		panel.add(uPanel);
		panel.add(pPanel);
		panel.add(login);
		
		root.add(panel);
	}

}
