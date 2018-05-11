package com.contracteasy.client.session.page;

import com.contracteasy.client.utility.Contract;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class ContractDetailsPage implements Page{
	
	private Contract contract = null;
	VerticalPanel mainPanel = new VerticalPanel();

	public ContractDetailsPage(Contract contract) {
		this.contract = contract;
	}

	@Override
	public void build(RootPanel root) {
		
		root.clear();
		
		HTML header = new HTML("<h1>" + contract.getDescription() + "</h1>");
		header.setStyleName("h1");
		
		mainPanel.add(header);
		
		addLabel("Reference", contract.getReference());
		addLabel("Client Reference", contract.getClientRef());
		addLabel("Type", contract.getType());
		addLabel("Counter Party", contract.getCounterParty());
		addLabel("Start Date", contract.getStartDate());
		addLabel("Termination Date", contract.getTerminationDate());
		addLabel("Renewal Date", contract.getRenewalDate());
		addLabel("Escalation Date", contract.getEscalationDate());
		addLabel("Notice Period", contract.getNoticePeriod() + " days");
		addLabel("Other actions needed", "");
		
		mainPanel.setStyleName("dataList");
		
		root.add(mainPanel);
	}
	
	private void addLabel(String key, String value) {
		HorizontalPanel panel = new HorizontalPanel();
		Label keyLabel = new Label(key);
		Label valueLabel = new Label(value);
		panel.add(keyLabel);
		panel.add(valueLabel);
		panel.setStylePrimaryName("dataLabel");
		mainPanel.add(panel);
	}
}
