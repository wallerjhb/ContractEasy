package com.contracteasy.client.communication.dto;

public interface DashboardDTO extends RequestDTO{

	int userId = 0;
	
	public int getUserId();
	public void setUserId(int userId);
}
