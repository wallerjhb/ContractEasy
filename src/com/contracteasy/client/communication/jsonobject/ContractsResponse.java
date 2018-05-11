package com.contracteasy.client.communication.jsonobject;

import com.google.gwt.core.client.JavaScriptObject;

public class ContractsResponse extends JavaScriptObject {
	
	protected ContractsResponse() {}
	
	public final native String getId() /*-{ return this.id; }-*/;
	public final native String getDesc() /*-{ return this.desc; }-*/;
	public final native String getStatus() /*-{ return this.status; }-*/;
	public final native String getReference() /*-{ return this.reference; }-*/;
	public final native String getClientRef() /*-{ return this.clientref; }-*/;
	public final native String getType() /*-{ return this.type; }-*/;
	public final native String getCounterParty() /*-{ return this.counterparty; }-*/;
	public final native String getStart() /*-{ return this.start; }-*/;
	public final native String getTermination() /*-{ return this.termination; }-*/;
	public final native String getEscalation() /*-{ return this.escalation; }-*/;
	public final native String getRenewal() /*-{ return this.renewal; }-*/;
	public final native String getNoticePeriod() /*-{ return this.noticeperioddays; }-*/;
	
}
