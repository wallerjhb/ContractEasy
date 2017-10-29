package com.contracteasy.client.session;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class SessionManager {

	private static SessionUser loggedInUser = null;
	
	public static void load(String page) {
		Window.alert("Loading new screen");
		
		RootPanel root = RootPanel.get("contentContainer");
		
		root.clear();
		VerticalPanel panel = new VerticalPanel();
		TextBox tb = new TextBox();
		panel.add(tb);
		Button submit = new Button();
		submit.setText("Sign Up");
		panel.add(submit);
		root.add(panel);
	}

	public static void loadButtons() {
		RootPanel root = RootPanel.get("signUpButtonContainer");
		root.clear();
		Button signUpButton = new Button();
		signUpButton.setText("SIGN UP");
		signUpButton.setStylePrimaryName("button big special");
		signUpButton.addClickHandler(new ClickHandler() {			
			@Override
			public void onClick(ClickEvent event) {
					Window.Location.assign("ContractEasyClient.html");			
			}
		});
		root.add(signUpButton);
	}
}
