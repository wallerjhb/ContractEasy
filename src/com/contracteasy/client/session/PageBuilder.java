package com.contracteasy.client.session;

import com.contracteasy.client.communication.AccessServerCaller;
import com.contracteasy.client.communication.ContractsServerCaller;
import com.contracteasy.client.communication.DetailsServerCaller;
import com.contracteasy.client.session.page.BankDetailsPage;
import com.contracteasy.client.session.page.CompanyDetailsPage;
import com.contracteasy.client.session.page.LoginPage;
import com.contracteasy.client.session.page.LoginPanel;
import com.contracteasy.client.session.page.PackageSelectionPage;
import com.contracteasy.client.session.page.SignUpButton;
import com.contracteasy.client.session.page.Page;
import com.contracteasy.client.session.page.SignUpPage;
import com.contracteasy.client.session.page.ThankYouPage;
import com.contracteasy.client.session.page.UploadPage;
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
		
		Window.alert("Loading page with " + action);
		
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
			case "new" : currentPage = new CompanyDetailsPage(user);
				break;
			case "packages" : DetailsServerCaller.getInstance().loadPackageOptions(user);
				break;
			case "bankDetails" : currentPage = new BankDetailsPage(user);
				break;
			case "dashboard" : {
				try {
					ContractsServerCaller.getInstance().countData(user);
					return;
				} catch (NumberFormatException e) {Window.alert("Cannot load Dashboard");}
			}
				break;
			case "contracts" : ContractsServerCaller.getInstance().getData(user, "co");
				return;
			case "alerts" : ContractsServerCaller.getInstance().getData(user, "al");
				return;
			case "notices" : ContractsServerCaller.getInstance().getData(user, "no");
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
	
	public static void loadLoginPanel(String user) {
		RootPanel root = RootPanel.get("loginContainer");
		root.clear();
		
		if (user != null && !user.isEmpty()) {
			AccessServerCaller.getInstance().loadLoginPanel(user);		
		} else {
			currentPage = new LoginPanel(null);
			currentPage.build(root);
		}
	}
	
}
