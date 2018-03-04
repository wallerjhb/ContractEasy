package com.contracteasy.client.session;

import com.contracteasy.client.communication.ServerCaller;
import com.contracteasy.client.session.page.LoginPage;
import com.contracteasy.client.session.page.LoginPanel;
import com.contracteasy.client.session.page.SignUpButton;
import com.contracteasy.client.session.page.Page;
import com.contracteasy.client.session.page.SignUpPage;
import com.contracteasy.client.session.page.ThankYouPage;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootPanel;

public class PageBuilder {
	
	static Page currentPage;
	
	public static void load(String action) {
		load(action, 0, null);
	}
	
	public static void load(String action, int user) {
		load(action, user, null);
	}
	
	public static void load(String action, int user, String arg) {
		
		RootPanel root = RootPanel.get("contentContainer");
		root.clear();
		
		if (action != null) {
			switch (action) {
			case "signUp" :	currentPage = new SignUpPage();
				break;
			case "thankYou" : currentPage = new ThankYouPage();
				break;
			case "login" : currentPage = new LoginPage();
				break;
			case "new" :
				break;
			case "dashboard" : {
				try {
					ServerCaller.getInstance().countData(user);
					return;
				} catch (NumberFormatException e) {Window.alert("Cannot load Dashboard");}
			}
				break;
			case "contracts" : ServerCaller.getInstance().getData(user, "co");
				return;
			case "admin" :
				break;
			default : break;
			}
		} else currentPage = new LoginPage();
		
		currentPage.build(root);

	}

	public static void loadButtons(SessionUser loggedInUser) {
		RootPanel root = RootPanel.get("signUpButtonContainer");
		root.clear();

		currentPage = new SignUpButton();
		
		currentPage.build(root);
	}
	
	public static void loadLoginPanel(SessionUser user) {
		RootPanel root = RootPanel.get("loginContainer");
		root.clear();
		
		currentPage = new LoginPanel(user);
		
		currentPage.build(root);
	}
	
}
