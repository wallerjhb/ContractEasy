package com.contracteasy.client.session.page;

import java.util.List;

import com.contracteasy.client.utility.Alert;
import com.google.gwt.user.client.ui.RootPanel;

public class AlertDetailsPage implements Page {

	private List<Alert> alerts;
	
	public AlertDetailsPage(List<Alert> alerts) {
		this.alerts = alerts;
	}
	
	@Override
	public void build(RootPanel root) {
		
	}

}
