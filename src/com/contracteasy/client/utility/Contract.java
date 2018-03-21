package com.contracteasy.client.utility;

public class Contract {
	
	private String id = "xxxx";
	private String reference = "xxxx";
	private String clientRef = "xxxx";
	private String description = "xxxx";
	private String startDate = "xxxx";
	private String terminationDate = "xxxx";
	private String renewalDate = "xxxx";
	private String escalationDate = "xxxx";
	private String type = "xxxx";
	private String counterParty = "xxxx";
	private String noticePeriod = "xxxx";	
	private String status = "xxxx";
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getReference() {
		return reference;
	}
	public void setReference(String reference) {
		this.reference = reference;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getTerminationDate() {
		return terminationDate;
	}
	public void setTerminationDate(String endDate) {
		this.terminationDate = endDate;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getCounterParty() {
		return counterParty;
	}
	public void setCounterParty(String counterParty) {
		this.counterParty = counterParty;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getClientRef() {
		return clientRef;
	}
	public void setClientRef(String clientRef) {
		this.clientRef = clientRef;
	}
	public String getRenewalDate() {
		return renewalDate;
	}
	public void setRenewalDate(String renewalDate) {
		this.renewalDate = renewalDate;
	}
	public String getEscalationDate() {
		return escalationDate;
	}
	public void setEscalationDate(String escalationDate) {
		this.escalationDate = escalationDate;
	}
	public String getNoticePeriod() {
		return noticePeriod;
	}
	public void setNoticePeriod(String noticePeriod) {
		this.noticePeriod = noticePeriod;
	}
}
