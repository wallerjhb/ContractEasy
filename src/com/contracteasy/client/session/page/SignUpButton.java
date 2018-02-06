package com.contracteasy.client.session.page;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.RootPanel;

public class SignUpButton implements Page {

	@Override
	public void build(RootPanel root) {

		Button signUpButton = new Button();
		signUpButton.setText("SIGN UP");
		signUpButton.setStylePrimaryName("button big special");
		signUpButton.addClickHandler(new ClickHandler() {			
			@Override
			public void onClick(ClickEvent event) {
					Window.Location.assign("ContractEasyClient.html?action=signUp");			
			}
		});
		root.add(signUpButton);
			
	}

}
