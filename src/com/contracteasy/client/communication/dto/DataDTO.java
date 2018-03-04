package com.contracteasy.client.communication.dto;

public interface DataDTO extends RequestDTO {
	
	String dataType = "";
	int userId = 0;
	
	public String getDataType();
	public void setDataType(String dataType);
	public int getUserId();
	public void setUserId(int userId);
}
