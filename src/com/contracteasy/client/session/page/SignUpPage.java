package com.contracteasy.client.session.page;

import com.contracteasy.client.communication.ServerCaller;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class SignUpPage implements Page{
	
	TextBox pw;
	TextBox pwConf;
	TextBox username;

	@Override
	public void build(RootPanel root) {
		VerticalPanel panel = new VerticalPanel();
		
		HorizontalPanel usernamePanel = new HorizontalPanel();
		Label uLabel = new Label("Please select a username");
		username = new TextBox();
		usernamePanel.add(uLabel);
		usernamePanel.add(username);

		HorizontalPanel pwPanel = new HorizontalPanel();
		Label pwLabel = new Label("Password");
		pw = new PasswordTextBox();
		pwPanel.add(pwLabel);
		pwPanel.add(pw);
		
		HorizontalPanel pwConfPanel = new HorizontalPanel();
		Label pwConfLabel = new Label("Confirm Password");
		pwConf = new PasswordTextBox();
		pwConfPanel.add(pwConfLabel);
		pwConfPanel.add(pwConf);
		
		panel.add(usernamePanel);
		panel.add(pwPanel);
		panel.add(pwConfPanel);
		
		Button submit = new Button();
		submit.setText("Sign Up");
		submit.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				if (pw.getText().equals(pwConf.getText())) {
					//ServerCaller.getInstance().signUp(username.getText(), Md5Utils.getMd5Digest(pw.getText().getBytes()).toString());
					ServerCaller.getInstance().signUp(username.getText(), pw.getText());
				} else {
					Window.alert("Your passwords don't match. Please ensure passwords match...");
					pw.setText("");
					pwConf.setText("");
				}
				
			}
		});
		panel.add(submit);
		root.add(panel);
	}

}
