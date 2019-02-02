package com.contracteasy.client.session.page;

import com.contracteasy.client.communication.AccessServerCaller;
import com.contracteasy.client.session.SessionUser;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class LoginPanel implements Page{
	
	private SessionUser user = null;
	private TextBox username;
	private TextBox password;
	
	public LoginPanel(SessionUser user) {
		this.user = user;
	}

	@Override
	public void build(RootPanel root) {
		
		root.clear();
		
		Window.alert("Loading login panel; Null user " + (user == null) ); 
		
		if (user == null) {
			HorizontalPanel panel = new HorizontalPanel();
			username = new TextBox();	
			password = new PasswordTextBox();
			
			Button login = new Button("Login");
			login.setStylePrimaryName("button special");
			
			login.addClickHandler(new ClickHandler() {
				
				@Override
				public void onClick(ClickEvent event) {
					AccessServerCaller.getInstance().login(username.getText(), password.getText());
				}
			});
			
			HTML label = new HTML("<pre class=\"gwt-Label\">Not a member? <a href=\"ContractEasyClient.html?action=signUp\">Sign Up</a></pre>");
			
			panel.add(username);
			panel.add(password);
			panel.add(login);
			panel.add(label);
			
			root.add(panel);
			
		} else {
			HorizontalPanel panel = new HorizontalPanel();
			Label label = new Label("Hello, " + user.getUsername());
			Button logOut = new Button("Logout");
			HTML account = new HTML("<pre class=\"gwt-Label\"><a href=\"ContractEasyClient.html?action=signUp\">Manage my account</a></pre>");
			
			panel.add(label);
			panel.add(logOut);
			panel.add(account);
			
			root.add(panel);
		}
	}

}
