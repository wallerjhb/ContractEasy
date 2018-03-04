package com.contracteasy.client.session.page;

import java.util.Date;
import java.util.List;

import com.contracteasy.client.utility.Constants;
import com.contracteasy.client.utility.Contract;
import com.google.gwt.cell.client.DateCell;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.TextColumn;
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
		
		TextColumn<Contract> counterParty = new TextColumn<Contract>() {

			@Override
			public String getValue(Contract object) {
				return object.getCounterParty();
			}
		};
		
		DateCell dateCell = new DateCell();
		Column<Contract,Date> startDate = new Column<Contract,Date>(dateCell) {

			@Override
			public Date getValue(Contract object) {
				return object.getStartDate();
			}
		};
		
		TextColumn<Contract> status = new TextColumn<Contract>() {

			@Override
			public String getValue(Contract object) {
				switch (object.getStatus()) {
				case Constants.CONTRACT_STATUS_ACTIVE : return "Active";
				case Constants.CONTRACT_STATUS_NOT_UPLOADED : return "Nothing on record";
				}
				
				return "";
			}
		};
		
		table.addColumn(reference, "Reference");
		table.addColumn(type, "Contract Type");
		table.addColumn(counterParty, "Counter Party");
		table.addColumn(startDate, "Start Date");
		table.addColumn(status, "Status");
		
		table.setRowData(contracts);
		
		root.add(table);
	}

}
