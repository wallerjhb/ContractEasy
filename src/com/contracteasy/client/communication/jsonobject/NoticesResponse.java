package com.contracteasy.client.communication.jsonobject;

import com.google.gwt.core.client.JavaScriptObject;

public class NoticesResponse extends JavaScriptObject{

	protected NoticesResponse() {}
	
	public final native String getId() /*-{ return this.id; }-*/;
	public final native String getDesc() /*-{ return this.desc; }-*/;
	public final native String getStatus() /*-{ return this.status; }-*/;
	public final native String getReference() /*-{ return this.reference; }-*/;
	public final native String getDateSent() /*-{ return this.datesent; }-*/;
	public final native String getContent() /*-{ return this.content; }-*/;
	public final native String getCounterParty() /*-{ return this.counterparty; }-*/;

}
