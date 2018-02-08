package com.contracteasy.client.session.page;

import com.contracteasy.client.communication.ServerCaller;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class DashboardPage implements Page{

	private int usid = 0;
	
	public DashboardPage(int usid) {
		this.usid = usid;
	}

	@Override
	public void build(RootPanel root) {
		
		HorizontalPanel top = new HorizontalPanel();
		VerticalPanel contracts = new VerticalPanel();
		
		Label contractsHeader = new Label();
		contractsHeader.setText("My Contracts");
		
		Image icon = new Image();
		icon.setUrl("images/contractIcon.png");
		icon.setSize("100px", "100px");
		
		Label label = new Label();
	
		int numContracts = ServerCaller.getInstance().contractCount(usid);
		
		if (numContracts == 1) {
			label.setText(numContracts + " Active Contract");
		} else {
			label.setText(numContracts + " Active Contracts");
		}
		
		contracts.add(contractsHeader);
		contracts.add(icon);
		contracts.add(label);
		
		contracts.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		
		top.add(contracts);
		
		root.add(top);
	}
}
