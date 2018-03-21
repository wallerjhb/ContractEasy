package com.contracteasy.client.session.page;

import java.util.Date;
import java.util.List;

import com.contracteasy.client.utility.Constants;
import com.contracteasy.client.utility.Contract;
import com.google.gwt.cell.client.ButtonCell;
import com.google.gwt.cell.client.DateCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootPanel;

public class ContractsPage implements Page {
	
	private List<Contract> contracts;
	
	public ContractsPage(List<Contract> contracts) {
		this.contracts = contracts;
	}

	@Override
	public void build(RootPanel root) {
		CellTable<Contract> table = new CellTable<Contract>();
		
		TextColumn<Contract> reference = new TextColumn<Contract>() {

			@Override
			public String getValue(Contract object) {
				return object.getReference();
			}
		};
		
		TextColumn<Contract> type = new TextColumn<Contract>() {

			@Override
			public String getValue(Contract object) {
				return object.getType();
			}
		};
		
		TextColumn<Contract> description = new TextColumn<Contract>() {

			@Override
			public String getValue(Contract object) {
				return object.getDescription();
			}
		};	
		
		TextColumn<Contract> counterParty = new TextColumn<Contract>() {

			@Override
			public String getValue(Contract object) {
				return object.getCounterParty();
			}
		};
		
		DateCell dateCell = new DateCell(DateTimeFormat.getFormat("EEE d MMM yyyy"));
		Column<Contract,Date> startDate = new Column<Contract,Date>(dateCell) {

			@Override
			public Date getValue(Contract object) {
				return DateTimeFormat.getFormat("yyyy-MM-dd").parse(object.getStartDate());
			}
		};
		
		TextColumn<Contract> status = new TextColumn<Contract>() {

			@Override
			public String getValue(Contract object) {
				switch (Integer.parseInt(object.getStatus())) {
				case Constants.CONTRACT_STATUS_ACTIVE : return "Active";
				case Constants.CONTRACT_STATUS_NOT_UPLOADED : return "Nothing on record";
				}
				
				return "";
			}
		};
		
		ButtonCell buttonCell = new ButtonCell();
		Column<Contract, String> buttonColumn = new Column<Contract, String>(buttonCell) {

			@Override
			public String getValue(Contract object) {
				if (Integer.parseInt(object.getStatus()) == Constants.CONTRACT_STATUS_ACTIVE) return "Download";
				else return "Upload";
			}
			
		};
		
		buttonColumn.setFieldUpdater(new FieldUpdater<Contract, String>() {
			  public void update(int index, Contract object, String value) {
			    Window.alert("You clicked: " + value + " " + index);
			  }
			});
		
		table.addColumn(reference, "Reference");
		table.addColumn(type, "Contract Type");
		table.addColumn(description, "Description");
		table.addColumn(counterParty, "Counter Party");
		table.addColumn(startDate, "Start Date");
		table.addColumn(status, "Status");
		table.addColumn(buttonColumn);
		
		Window.alert("Contracts size " + contracts.size());
		table.setRowData(contracts);
		
		root.add(table);
	}

}
