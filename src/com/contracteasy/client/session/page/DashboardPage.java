package com.contracteasy.client.session.page;

import com.contracteasy.client.session.PageBuilder;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class DashboardPage implements Page{

	private int userId;
	private int numContracts;
	private int numNotices;
	private int numAlerts;
	
	public DashboardPage(int userId, int numContracts, int numNotices, int numAlerts) {
		this.userId = userId;
		this.numContracts = numContracts;
		this.numAlerts = numAlerts;
		this.numNotices = numNotices;
	}

	@Override
	public void build(RootPanel root) {
		
		HorizontalPanel top = new HorizontalPanel();
		VerticalPanel contracts = new VerticalPanel();
		
		Label contractsHeader = new Label();
		contractsHeader.setText("My Contracts");
		
		Image contractsIcon = new Image();
		contractsIcon.setUrl("images/contractIcon.png");
		contractsIcon.setSize("250px", "250px");
		
		contractsIcon.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				PageBuilder.load("contracts", userId);			
			}
		});
		
		Label contractsLabel = new Label();
	
		if (numContracts == 1) {
			contractsLabel.setText(numContracts + " Active Contract");
		} else {
			contractsLabel.setText(numContracts + " Active Contracts");
		}
		
		contracts.add(contractsHeader);
		contracts.add(contractsIcon);
		contracts.add(contractsLabel);
		
		contracts.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		
		VerticalPanel alerts = new VerticalPanel();
		
		Label alertsHeader = new Label();
		alertsHeader.setText("My Alerts");
		
		Image alertsIcon = new Image();
		alertsIcon.setUrl("images/contractIcon.png");
		alertsIcon.setSize("250px", "250px");
		
		Label alertsLabel = new Label();
	
		if (numAlerts == 1) {
			alertsLabel.setText(numAlerts + " Active Alert");
		} else {
			alertsLabel.setText(numAlerts + " Active Alerts");
		}
		
		alerts.add(alertsHeader);
		alerts.add(alertsIcon);
		alerts.add(alertsLabel);
		
		alertsIcon.getElement().setAttribute("align", "center");
		
		top.setStylePrimaryName("dashboard");
		
		top.add(contracts);
		top.add(alerts);
		
		root.add(top);
	}
}
