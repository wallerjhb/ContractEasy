package com.contracteasy.client.session.page;

import com.contracteasy.client.communication.DetailsServerCaller;
import com.contracteasy.client.utility.BankDetails;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class BankDetailsPage implements Page {
	
	private int user;
	
	private VerticalPanel panel = new VerticalPanel();
	
	public BankDetailsPage(int user) {
		this.user = user;
	}

	@Override
	public void build(RootPanel root) {
		panel.add(new Label("Please provide your bank details so that we may begin your debit order"));
		
		final TextBox account = input(new TextBox(), "Account Number");
		final TextBox branch = input(new TextBox(), "Branch Code");
		final TextBox accountName = input(new TextBox(), "Name on Account");
		
		Button submit = new Button("Submit");
		
		submit.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				BankDetails details = new BankDetails();
				details.setAccountNum(account.getText());
				details.setBranch(branch.getText());
				details.setAccountName(accountName.getText());
				details.setUser(Integer.toString(user));
				DetailsServerCaller.getInstance().uploadBankDetails(details);
			}
		});
		
		panel.add(submit);
		root.add(panel);
	}

	private TextBox input(TextBox box, String label) {
		HorizontalPanel hPanel = new HorizontalPanel();
		hPanel.add(new Label(label));
		hPanel.add(box);
		panel.add(hPanel);
		return box;
	}
}
