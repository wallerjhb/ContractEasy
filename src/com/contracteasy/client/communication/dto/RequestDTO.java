package com.contracteasy.client.communication.dto;

public interface RequestDTO {

	String request = "";
	String user = "";

	String getRequest();
	void setRequest(String request);
	public String getUser();
	public void setUser(String user);
}
