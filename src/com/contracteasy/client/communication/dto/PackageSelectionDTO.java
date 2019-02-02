package com.contracteasy.client.communication.dto;

public interface PackageSelectionDTO extends RequestDTO {
	
	String user = "";
	String pkg = "";
	
	public String getUser();
	public void setUser(String user);
	public String getPkg();
	public void setPkg(String pkg);
}
