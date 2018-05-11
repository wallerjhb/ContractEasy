package com.contracteasy.client.communication.dto;

public interface DetailsDTO extends RequestDTO {

	String dataType = "";
	int id = 0;
	
	public String getDataType();
	public void setDataType(String dataType);
	public int getId();
	public void setId(int id);
}
