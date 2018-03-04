package com.contracteasy.client.communication.jsonobject;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.json.client.JSONArray;

public class ContractsResponse extends JavaScriptObject {
	
	protected ContractsResponse() {}
	
	public final native JSONArray getContracts() /*-{ return this.contracts; }-*/;

}
