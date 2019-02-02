package com.contracteasy.client.session.page;

import java.util.Date;
import java.util.List;

import com.contracteasy.client.communication.AccessServerCaller;
import com.contracteasy.client.communication.ContractsServerCaller;
import com.contracteasy.client.utility.Notice;
import com.google.gwt.cell.client.ClickableTextCell;
import com.google.gwt.cell.client.DateCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootPanel;

public class NoticesPage implements Page {
	
	private List<Notice> notices;
	
	public NoticesPage(List<Notice> notices) {
		this.notices = notices;
	}

	@Override
	public void build(RootPanel root) {
		
		Window.alert("Building notices page");
		
		try {
			CellTable<Notice> table = new CellTable<Notice>();
			
			Column<Notice, String> reference = new Column<Notice, String>(new ClickableTextCell())  {
				@Override
				public String getValue(Notice object)  {
					return object.getReference();
				}
			};
			reference.setFieldUpdater(new FieldUpdater<Notice, String>() {
				@Override
				public void update(int index, Notice object, String value) {
					Window.alert("Clicking notice id: " + object.getId());
					ContractsServerCaller.getInstance().getDetails(Integer.parseInt(object.getId()), "no");
				}
			});
			
			TextColumn<Notice> counterParty = new TextColumn<Notice>() {

				@Override
				public String getValue(Notice object) {
					return object.getCounterParty();
				}
			};
			
			TextColumn<Notice> nature = new TextColumn<Notice>() {

				@Override
				public String getValue(Notice object) {
					return object.getDescription();
				}
			};
			
			DateCell dateCell = new DateCell(DateTimeFormat.getFormat("EEE d MMM yyyy"));
			Column<Notice,Date> dateSent = new Column<Notice,Date>(dateCell) {

				@Override
				public Date getValue(Notice object) {
					return DateTimeFormat.getFormat("yyyy-MM-dd").parse(object.getDateSent());
				}
			};
			
			table.addColumn(reference, "Notice Reference");
			table.addColumn(counterParty, "Counter Party");
			table.addColumn(nature, "Nature of Notice");
			table.addColumn(dateSent, "Date Sent");
			
			table.setRowData(notices);
			
			root.add(table);
		} catch (Exception e) {
			Window.alert(e.getMessage());
		}
	}

}
