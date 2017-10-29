package com.contracteasy.client.communication;

import com.contracteasy.client.client.ContractEasyClient;
import com.contracteasy.client.communication.dto.UserDTO;
import com.contracteasy.client.communication.jsonobject.SignUpResponse;
import com.contracteasy.client.session.SessionManager;
import com.google.gwt.core.client.JsonUtils;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.http.client.URL;
import com.google.gwt.user.client.Window;
import com.google.web.bindery.autobean.shared.AutoBean;
import com.google.web.bindery.autobean.shared.AutoBeanCodex;
import com.google.web.bindery.autobean.shared.AutoBeanUtils;

public class ServerCaller {

	public ServerCaller instance;
	
	private RequestBuilder builder;
	
	private ServerCaller() {}
	
	public ServerCaller getInstance() {
		if (instance == null) instance = new ServerCaller();
		return instance;
	}
	
	public void signUp(String username, String password) {
		String url = ContractEasyClient.SERVER + "access.php";
		
		URL.encode(url);
		
		UserDTO dto = new UserDTO();
		dto.setUsername(username);
		dto.setPassword(password);
		
	    builder = new RequestBuilder(RequestBuilder.POST, url);
	    builder.setHeader("Content-Type", "application/json");
	    
	    try {
	    	
	    	AutoBean<UserDTO> bean = AutoBeanUtils.getAutoBean(dto);
		    builder.sendRequest(AutoBeanCodex.encode(bean).getPayload(), new RequestCallback() {
		        
		    	  public void onError(Request request, Throwable exception) {
		    		  Window.alert("Unable to communicate with ContractEasy Server. Please try again later.");
		    	  }

		    	  public void onResponseReceived(Request request, Response response) {
		    		  if (200 == response.getStatusCode()) {		
		    			  SignUpResponse signUpResponse = JsonUtils.<SignUpResponse>safeEval(response.getText());
		    			  if (signUpResponse.getErrorCode() != 0) {
		    				  Window.alert(signUpResponse.getErrorMessage());
		    			  } else SessionManager.load("login");
		    			  
		    		  } else { Window.alert("Unable to sign up."); }
		        }
		      });
		    } catch (RequestException e) { Window.alert("Couldn't retrieve JSON"); }
	}
}
