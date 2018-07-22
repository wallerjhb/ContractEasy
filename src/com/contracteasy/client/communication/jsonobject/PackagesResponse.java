package com.contracteasy.client.communication.jsonobject;

import com.google.gwt.core.client.JavaScriptObject;

public class PackagesResponse extends JavaScriptObject {
	
	protected PackagesResponse() {}
	
	public final native String getName() /*-{ return this.name; }-*/;
	public final native String getMax() /*-{ return this.max; }-*/;

}
