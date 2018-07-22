package com.contracteasy.client.session.page;

import java.util.List;

import com.contracteasy.client.utility.ContractPackage;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class PackageSelectionPage implements Page {
	
	List<ContractPackage> packages;
	
	public PackageSelectionPage(List<ContractPackage> packages, int user) {
		this.packages = packages;
	}

	@Override
	public void build(RootPanel root) {
		
		root.clear();
		
		VerticalPanel panel = new VerticalPanel();
		
		panel.add(new Label("Thank you for registering with Contract Easy"));
		panel.add(new Label("Choose which package you'd like"));
		
		FlexTable flex = new FlexTable();
		flex.setText(0, 0, "");
		flex.setText(1, 0, "Maximum number of contracts");
		flex.setText(2, 0, "");
		
		int column = 1;
		for (ContractPackage p : packages) {
			flex.setText(0, column, p.getName());
			flex.setText(1, column, p.getMax().equals("-1")?"Unlimited":p.getMax());
			Button signUp = new Button("Get");
			final String id = p.getId();
			signUp.addClickHandler(new ClickHandler() {
				
				@Override
				public void onClick(ClickEvent event) {
					Window.alert(id);
				}
			});
			flex.setWidget(2, column++, signUp);
		}
		
		panel.add(flex);
		
		root.add(panel);
	}
	
	
}
