package com.contracteasy.client.communication.jsonobject;

import com.google.gwt.core.client.JavaScriptObject;

public class LoginResponse extends JavaScriptObject {
	
	protected LoginResponse() {}
	
	public final native int getErrorCode() /*-{ return this.errorCode; }-*/;
	public final native String getErrorMessage() /*-{ return this.errorMessage; }-*/;
	public final native String getUsid() /*-{ return this.usid; }-*/;
	public final native String getStatus() /*-{ return this.status; }-*/;
	public final native String getPackage() /*-{ return this.pkg; }-*/;
	public final native String getUsername() /*-{ return this.username; }-*/;
	public final native String getName() /*-{ return this.name; }-*/;
}
