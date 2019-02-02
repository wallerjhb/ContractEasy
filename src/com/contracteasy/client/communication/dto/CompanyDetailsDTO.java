package com.contracteasy.client.communication.dto;

public interface CompanyDetailsDTO extends RequestDTO {
	
	String companyName = "";
	String email = "";
	String contactName = "";
	String physicalAddress = "";

	public String getCompanyName();
	public void setCompanyName(String companyName);
	public String getEmail();
	public void setEmail(String email);
	public String getContactName();
	public void setContactName(String contactName);
	public String getPhysicalAddress();
	public void setPhysicalAddress(String physicalAddress);
}
