package com.contracteasy.client.communication.dto;

public interface BankDetailsDTO extends RequestDTO {

	String user = "";
	String accountNum = "";
	String branch = "";
	String accountName = "";
	
	public String getUser();
	public void setUser(String user);
	public String getAccountNum();
	public void setAccountNum(String accountNum);
	public String getBranch();
	public void setBranch(String branch);
	public String getAccountName();
	public void setAccountName(String accountName);
	
	
}
