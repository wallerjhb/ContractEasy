package com.contracteasy.client.communication.jsonobject;

import com.google.gwt.core.client.JavaScriptObject;

public class DashboardResponse extends JavaScriptObject {

	protected DashboardResponse() {}
	
	public final native int getContractCount() /*-{ return this.contracts; }-*/;
	public final native int getAlertCount() /*-{ return this.alerts; }-*/;
	public final native int getNoticeCount() /*-{ return this.notices; }-*/;
}
