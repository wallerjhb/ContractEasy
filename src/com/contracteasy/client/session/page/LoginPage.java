package com.contracteasy.client.session.page;

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
		
		panel.add(uPanel);
		panel.add(pPanel);
		panel.add(login);
		
		root.add(panel);
	}

}
