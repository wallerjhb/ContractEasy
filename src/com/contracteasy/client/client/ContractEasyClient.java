package com.contracteasy.client.client;

import com.contracteasy.client.session.PageBuilder;
import com.contracteasy.client.session.SessionManager;
import com.contracteasy.client.session.page.SignUpButton;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class ContractEasyClient implements EntryPoint {
	
	public static final String SERVER = "contracteasyserver/";
	private SessionManager session = new SessionManager();

	@Override
	public void onModuleLoad() {
		
		String action = Window.Location.getParameter("action");
		String user = Window.Location.getParameter("user");

		RootPanel signUp = RootPanel.get("signUpButtonContainer");
		if (signUp != null && signUp.isAttached()) {
			PageBuilder.loadButtons(session.getLoggedInUser());
		}
		
		RootPanel content = RootPanel.get("contentContainer");
		if (content != null && content.isAttached()) {
			PageBuilder.load(action, Integer.parseInt(user));
		}
		
		RootPanel loginPanel = RootPanel.get("loginContainer");
		if (loginPanel != null && loginPanel.isAttached()) {
			PageBuilder.loadLoginPanel(null);
		}
	}
	
	
}
