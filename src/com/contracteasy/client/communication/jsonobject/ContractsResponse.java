package com.contracteasy.client.communication.jsonobject;

import com.google.gwt.core.client.JavaScriptObject;

public class ContractsResponse extends JavaScriptObject {
	
	protected ContractsResponse() {}
	
	public final native JavaScriptObject getContracts() /*-{ return this.contracts; }-*/;

}
