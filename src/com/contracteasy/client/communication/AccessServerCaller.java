package com.contracteasy.client.communication;

import java.util.ArrayList;

import com.contracteasy.client.client.ContractEasyClient;
import com.contracteasy.client.communication.dto.CompanyDetailsDTO;
import com.contracteasy.client.communication.dto.DashboardDTO;
import com.contracteasy.client.communication.dto.DataDTO;
import com.contracteasy.client.communication.dto.DetailsDTO;
import com.contracteasy.client.communication.dto.RequestDTO;
import com.contracteasy.client.communication.dto.UserDTO;
import com.contracteasy.client.communication.jsonobject.ContractsResponse;
import com.contracteasy.client.communication.jsonobject.DashboardResponse;
import com.contracteasy.client.communication.jsonobject.LoginResponse;
import com.contracteasy.client.communication.jsonobject.NoticesResponse;
import com.contracteasy.client.communication.jsonobject.SignUpResponse;
import com.contracteasy.client.communication.dto.ServerRequestFactory;
import com.contracteasy.client.session.PageBuilder;
import com.contracteasy.client.session.SessionUser;
import com.contracteasy.client.session.page.ContractDetailsPage;
import com.contracteasy.client.session.page.ContractsPage;
import com.contracteasy.client.session.page.DashboardPage;
import com.contracteasy.client.session.page.LoginPanel;
import com.contracteasy.client.session.page.NoticeDetailsPage;
import com.contracteasy.client.session.page.NoticesPage;
import com.contracteasy.client.session.page.PackageSelectionPage;
import com.contracteasy.client.session.page.Page;
import com.contracteasy.client.utility.CompanyDetails;
import com.contracteasy.client.utility.Constants;
import com.contracteasy.client.utility.Contract;
import com.contracteasy.client.utility.ContractPackage;
import com.contracteasy.client.utility.Notice;
import com.google.gwt.core.client.JsonUtils;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.http.client.URL;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.web.bindery.autobean.shared.AutoBean;
import com.google.web.bindery.autobean.shared.AutoBeanCodex;
import com.google.web.bindery.autobean.shared.AutoBeanUtils;

public class AccessServerCaller extends ServerCaller{

	public static AccessServerCaller instance;

	private RequestBuilder builder;
	private ServerRequestFactory factory = GWT.create(ServerRequestFactory.class);

	private AccessServerCaller() {}

	public static AccessServerCaller getInstance() {
		if (instance == null) instance = new AccessServerCaller();
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
							case Constants.USER_STATUS_ACTIVE : 
								if (loginResponse.getPackage().equals("0")) {
									Window.Location.assign("ContractEasyClient.html?action=packages&user=" + loginResponse.getUsid());
								} else {
									Window.Location.assign("ContractEasyClient.html?action=dashboard&user=" + loginResponse.getUsid());
								}
								break;
							case Constants.USER_STATUS_NEW_USER : 
								Window.Location.assign("ContractEasyClient.html?action=new&user=" + loginResponse.getUsid());			
								break;
							case Constants.USER_STATUS_ADMIN :

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


	public void logout(String user) {
		
	}

	public void loadLoginPanel(String user) {
		
		String url = ContractEasyClient.SERVER + "access.php";

		URL.encode(url);

		builder = new RequestBuilder(RequestBuilder.POST, url);
		builder.setHeader("Content-Type", "application/json");
		
		try {
			AutoBean<RequestDTO> dataDto = factory.requestDTO();

			dataDto.as().setRequest("getUser");
			dataDto.as().setUser(user);
			
			AutoBean<RequestDTO> bean = AutoBeanUtils.getAutoBean(dataDto.as());

			builder.sendRequest(AutoBeanCodex.encode(bean).getPayload(), new RequestCallback() {

				@Override
				public void onResponseReceived(Request request, Response response) {
					if (200 == response.getStatusCode()) {		
						LoginResponse loginResponse = JsonUtils.<LoginResponse>safeEval(response.getText());
						SessionUser user = new SessionUser();
						user.setUsername(loginResponse.getUsername());
						Page loginPanel = new LoginPanel(user);
						loginPanel.build(RootPanel.get("loginContainer"));
					}
				}

				@Override
				public void onError(Request request, Throwable exception) {
					// TODO Auto-generated method stub
					
				}
				
			});
		} catch (RequestException e) {
			e.printStackTrace();
		}
		
		/*currentPage = new LoginPanel(user);
		
		currentPage.build(root);*/
	}
}
