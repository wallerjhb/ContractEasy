package com.contracteasy.client.communication;

import com.contracteasy.client.client.ContractEasyClient;
import com.contracteasy.client.communication.dto.UserDTO;
import com.contracteasy.client.communication.jsonobject.LoginResponse;
import com.contracteasy.client.communication.jsonobject.SignUpResponse;
import com.contracteasy.client.communication.dto.ServerRequestFactory;
import com.contracteasy.client.session.PageBuilder;
import com.contracteasy.client.session.SessionManager;
import com.contracteasy.client.utility.Constants;
import com.google.gwt.core.client.JsonUtils;
import com.google.gwt.core.shared.GWT;
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

	public static ServerCaller instance;

	private RequestBuilder builder;
	private ServerRequestFactory factory = GWT.create(ServerRequestFactory.class);
	
	private static int contractCount;

	private ServerCaller() {}

	public static ServerCaller getInstance() {
		if (instance == null) instance = new ServerCaller();
		return instance;
	}

	public void signUp(String username, String password) {
		String url = ContractEasyClient.SERVER + "access.php";

		URL.encode(url);

		builder = new RequestBuilder(RequestBuilder.POST, url);
		builder.setHeader("Content-Type", "application/json");

		try {
			AutoBean<UserDTO> userDto = factory.userDto();

			userDto.as().setRequest("signUp");
			userDto.as().setUsername(username);
			userDto.as().setPassword(password);

			AutoBean<UserDTO> bean = AutoBeanUtils.getAutoBean(userDto.as());
			builder.sendRequest(AutoBeanCodex.encode(bean).getPayload(), new RequestCallback() {

				public void onError(Request request, Throwable exception) {
					Window.alert("Unable to communicate with ContractEasy Server. Please try again later.");
				}

				public void onResponseReceived(Request request, Response response) {
					if (200 == response.getStatusCode()) {		
						SignUpResponse signUpResponse = JsonUtils.<SignUpResponse>safeEval(response.getText());
						if (signUpResponse.getErrorCode() != 0) {
							Window.alert(signUpResponse.getErrorMessage());
						} else {
							PageBuilder.load("thankYou");
						}

					} else { Window.alert("Unable to sign up -- " + response.getStatusCode() + " " + response.getStatusText()); }
				}
			});
		} catch (RequestException e) { Window.alert("Couldn't retrieve JSON"); }
	}

	public void login(String username, String password) {
			
		if (username == null || username.equals("")) {
			Window.alert("Please enter a valid username");
			return;
		}

		if (password == null || password.isEmpty()) {
			Window.alert("Please enter your password");
			return;
		}

		String url = ContractEasyClient.SERVER + "access.php";

		URL.encode(url);

		builder = new RequestBuilder(RequestBuilder.POST, url);
		builder.setHeader("Content-Type", "application/json");

		try {
			AutoBean<UserDTO> userDto = factory.userDto();

			userDto.as().setRequest("login");
			userDto.as().setUsername(username);
			userDto.as().setPassword(password);

			AutoBean<UserDTO> bean = AutoBeanUtils.getAutoBean(userDto.as());
			builder.sendRequest(AutoBeanCodex.encode(bean).getPayload(), new RequestCallback() {

				@Override
				public void onResponseReceived(Request request, Response response) {
					if (200 == response.getStatusCode()) {		
						LoginResponse loginResponse = JsonUtils.<LoginResponse>safeEval(response.getText());
						if (loginResponse.getErrorCode() != 0) {
							Window.alert(loginResponse.getErrorMessage());
						} else {
							switch (Integer.parseInt(loginResponse.getStatus())) {
							case Constants.STATUS_ACTIVE : 
								Window.Location.assign("ContractEasyClient.html?action=dashboard&user=" + loginResponse.getUsid());			
								break;
							case Constants.STATUS_NEW_USER : 
								PageBuilder.load("new");
								break;
							case Constants.STATUS_AWAITING_QUOTE : 
								PageBuilder.load("awaitingQuote");
								break;
							case Constants.STATUS_QUOTE_RECEIVED : 
								PageBuilder.load("quoteAccept");
								break;
							default : Window.alert("Loading default status " + loginResponse.getStatus());
								break;
							}
						}
					} else { Window.alert("Unable to login -- " + response.getStatusCode() + " " + response.getStatusText()); }
				}

				@Override
				public void onError(Request request, Throwable exception) {
					Window.alert("Unable to communicate with ContractEasy Server. Please try again later.");					
				}
			});

		} catch (RequestException e) { Window.alert("Couldn't retrieve JSON"); }
	}
	
	public int contractCount(int userId) {
		
		String url = ContractEasyClient.SERVER + "contracts.php?r=numActive&u=" + userId;
		
		URL.encode(url);

		builder = new RequestBuilder(RequestBuilder.GET, url);
		
		try {
			builder.sendRequest(null, new RequestCallback() {
				
				@Override
				public void onResponseReceived(Request request, Response response) {
					if (200 == response.getStatusCode()) {		
						contractCount = Integer.parseInt(response.getText());
					} else { Window.alert("Unable to login -- " + response.getStatusCode() + " " + response.getStatusText()); }					
				}
				
				@Override
				public void onError(Request request, Throwable exception) {
					Window.alert("Unable to communicate with ContractEasy Server. Please try again later.");								
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
			
		return contractCount;
	}
}
