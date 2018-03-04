package com.contracteasy.client.communication.jsonobject;

import java.util.Date;

import com.google.gwt.core.client.JavaScriptObject;

public class ContractJSObject extends JavaScriptObject {
	
	protected ContractJSObject() {}

	public final native int getId() /*-{ return this.id; }-*/;
	public final native String getDescription() /*-{ return this.desc; }-*/;
	public final native String getReference() /*-{ return this.ref; }-*/;
	public final native Date getStartDate() /*-{ return this.start; }-*/;
	public final native Date getEndDate() /*-{ return this.end; }-*/;
	public final native String getType() /*-{ return this.type; }-*/;
	public final native String getCounterParty() /*-{ return this.counterParty; }-*/;
	public final native int getStatus() /*-{ return this.status; }-*/;

}
