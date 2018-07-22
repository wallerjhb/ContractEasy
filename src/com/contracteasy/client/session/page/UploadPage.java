package com.contracteasy.client.session.page;

import com.contracteasy.client.client.ContractEasyClient;
import com.contracteasy.client.utility.Contract;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlexTable.FlexCellFormatter;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;
import com.google.gwt.user.client.ui.FormPanel.SubmitEvent;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class UploadPage implements Page {

	@Override
	public void build(RootPanel root) {

		root.clear();

		// Create a FormPanel and point it at a service.
		final FormPanel form = new FormPanel();
		form.setAction(ContractEasyClient.SERVER + "files.php");

		// Because we're going to add a FileUpload widget, we'll need to set the
		// form to use the POST method, and multipart MIME encoding.
		form.setEncoding(FormPanel.ENCODING_MULTIPART);
		form.setMethod(FormPanel.METHOD_POST);

		// Create a panel to hold all of the form widgets.
		VerticalPanel panel = new VerticalPanel();
		form.setWidget(panel);

		final FlexTable flexTable = new FlexTable();
		FlexCellFormatter cellFormatter = flexTable.getFlexCellFormatter();
		addRow(flexTable);
		
		panel.add(flexTable);
		
		panel.add(new Button("Add another", new ClickHandler() {
			public void onClick(ClickEvent event) {
				addRow(flexTable);
			}
		}));

		// Add a 'submit' button.
		panel.add(new Button("Submit", new ClickHandler() {
			public void onClick(ClickEvent event) {
				form.submit();
			}
		}));

		// Add an event handler to the form.
		form.addSubmitHandler(new FormPanel.SubmitHandler() {
			public void onSubmit(SubmitEvent event) {
				// This event is fired just before the form is submitted. We can take
				// this opportunity to perform validation.

			}
		});
		form.addSubmitCompleteHandler(new FormPanel.SubmitCompleteHandler() {
			public void onSubmitComplete(SubmitCompleteEvent event) {
				// When the form submission is successfully completed, this event is
				// fired. Assuming the service returned a response of type text/html,
				// we can get the result text here (see the FormPanel documentation for
				// further explanation).
				Window.alert(event.getResults());
			}
		});

		root.add(form);
	}

	/**
	 * Add a row to the flex table.
	 */
	private void addRow(final FlexTable flexTable) {
		int numRows = flexTable.getRowCount();
		FileUpload upload = new FileUpload();
		upload.setName("uploadFormElement" + numRows);
		flexTable.setWidget(numRows, 0, new Label("Upload a file for your contract..."));
		flexTable.setWidget(numRows, 1, upload);
		flexTable.getFlexCellFormatter().setRowSpan(0, 1, numRows + 1);
		
		Button remove = new Button("Remove");
		remove.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				 int rowIndex = flexTable.getCellForEvent(event).getRowIndex();
			     flexTable.removeRow(rowIndex);				
			}
		});
		
		flexTable.setWidget(numRows, 2, remove);
	}

}
