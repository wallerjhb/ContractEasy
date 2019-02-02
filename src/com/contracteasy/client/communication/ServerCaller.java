package com.contracteasy.client.communication;

import com.google.gwt.json.client.JSONString;

public class ServerCaller {

	protected String stringOrNull(JSONString json) {
		if (json != null) return json.toString().replaceAll("\"", "");
		else return "";
	}
}
