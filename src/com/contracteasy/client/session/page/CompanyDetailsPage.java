package com.contracteasy.client.session.page;

import com.contracteasy.client.communication.AccessServerCaller;
import com.contracteasy.client.communication.DetailsServerCaller;
import com.contracteasy.client.utility.CompanyDetails;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class CompanyDetailsPage implements Page {
	
	private VerticalPanel panel = new VerticalPanel();
	private CompanyDetails details = new CompanyDetails();
	private int user = 0;
	
	public CompanyDetailsPage(int user) {
		this.user = user;
	}

	@Override
	public void build(RootPanel root) {
		panel.add(new Label("Please enter the details of your company"));

		final TextBox companyName = input(new TextBox(), "Company Name:");
		final TextBox contactPerson = input(new TextBox(), "Contact Person:");
		final TextBox email = input(new TextBox(), "Email Address:");
		final TextBox address = input(new TextBox(), "Physical Address:");
		
		root.add(panel);
		
		Button cont = new Button("Continue");
		cont.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				details.setUser(user);
				details.setCompanyName(companyName.getText());
				details.setContactName(contactPerson.getText());
				details.setEmail(email.getText());
				details.setPhysicalAddress(address.getText());
				DetailsServerCaller.getInstance().submitCompanyDetails(details);
			}
		});
		
		panel.add(cont);
	}
		
	private TextBox input(TextBox box, String label) {
		HorizontalPanel hPanel = new HorizontalPanel();
		hPanel.add(new Label(label));
		hPanel.add(box);
		panel.add(hPanel);
		return box;
	}
}
