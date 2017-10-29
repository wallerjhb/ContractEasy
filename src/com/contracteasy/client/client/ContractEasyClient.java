package com.contracteasy.client.client;

import com.contracteasy.client.session.SessionManager;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class ContractEasyClient implements EntryPoint {
	
	public static final String SERVER = "localhost:8080/server/";

	@Override
	public void onModuleLoad() {
		if (RootPanel.get("signUpButtonContainer").isAttached()) {
			SessionManager.loadButtons();
			Window.alert("Loading buttons");
		} else Window.alert("not loading buttons");
		
		if (RootPanel.get("contentContainer").isAttached()) {
			Window.alert("loading content");
			SessionManager.load("SignUp");
		} else Window.alert("Not loading content");
	}
	
	
}
