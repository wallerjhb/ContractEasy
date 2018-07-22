package com.contracteasy.client.session.page;

import com.contracteasy.client.utility.Notice;
import com.google.gwt.user.client.ui.RootPanel;

public class NoticeDetailsPage implements Page {
	
	private Notice notice;
	
	public NoticeDetailsPage(Notice notice) {
		this.notice = notice;
	}

	@Override
	public void build(RootPanel root) {
		
	}

}
