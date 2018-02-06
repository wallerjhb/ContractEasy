package com.contracteasy.client.communication.dto;

public interface UserDTO extends RequestDTO{
	
	String password = "";
	String username = "";
	
	public String getPassword();
	public void setPassword(String password);
	public String getUsername();
	public void setUsername(String username);

}
