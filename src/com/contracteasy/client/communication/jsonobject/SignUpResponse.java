package com.contracteasy.client.communication.jsonobject;

import com.google.gwt.core.client.JavaScriptObject;

public class SignUpResponse extends JavaScriptObject{
	
	protected SignUpResponse() {}
	
	public final native int getErrorCode() /*-{ return this.errorCode; }-*/;
	public final native String getErrorMessage() /*-{ return this.errorMessage; }-*/;
}
