package com.contracteasy.client.communication.jsonobject;

import com.google.gwt.core.client.JavaScriptObject;

public class DetailsResponse extends JavaScriptObject {

	protected DetailsResponse() {}
	
	public final native int getErrorCode() /*-{ return this.errorCode; }-*/;
}
