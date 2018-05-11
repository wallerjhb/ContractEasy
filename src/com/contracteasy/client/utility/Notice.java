package com.contracteasy.client.utility;

public class Notice {
	
	private String id;
	private String contractRef;
	private String status;
	private String reference;
	private String description;
	private String nature;
	private String dateSent;
	private String content;
	private String counterParty;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getContractRef() {
		return contractRef;
	}
	public void setContractRef(String contractRef) {
		this.contractRef = contractRef;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
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
	public String getNature() {
		return nature;
	}
	public void setNature(String nature) {
		this.nature = nature;
	}
	public String getDateSent() {
		return dateSent;
	}
	public void setDateSent(String dateSent) {
		this.dateSent = dateSent;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getCounterParty() {
		return counterParty;
	}
	public void setCounterParty(String counterParty) {
		this.counterParty = counterParty;
	}
}
