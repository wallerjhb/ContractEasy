package com.contracteasy.client.session;

public class SessionManager {
	
	public String action = "";
	
	private SessionUser loggedInUser = null;

	public SessionUser getLoggedInUser() {
		return loggedInUser;
	}

	public void setLoggedInUser(SessionUser loggedInUser) {
		this.loggedInUser = loggedInUser;
	}
	

}
