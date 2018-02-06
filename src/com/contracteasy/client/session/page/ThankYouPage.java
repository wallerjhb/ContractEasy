package com.contracteasy.client.session.page;

import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class ThankYouPage implements Page {

	@Override
	public void build(RootPanel root) {
		VerticalPanel panel = new VerticalPanel();
		Label label = new Label("Thank you for registering");
		panel.add(label);
		HorizontalPanel logInPanel = new HorizontalPanel();
		Label login = new Label("If you're ready start using Contract Easy, you can begin by ");
		Anchor link = new Anchor("logging in","ContractEasyClient.html?action=login");
		logInPanel.add(login);
		logInPanel.add(link);
		root.add(panel);
		root.add(logInPanel);
	}

}
