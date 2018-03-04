package com.contracteasy.client.communication;

import java.util.ArrayList;
import java.util.List;

import com.contracteasy.client.client.ContractEasyClient;
import com.contracteasy.client.communication.dto.DashboardDTO;
import com.contracteasy.client.communication.dto.DataDTO;
import com.contracteasy.client.communication.dto.UserDTO;
import com.contracteasy.client.communication.jsonobject.ContractJSObject;
import com.contracteasy.client.communication.jsonobject.ContractsResponse;
import com.contracteasy.client.communication.jsonobject.DashboardResponse;
import com.contracteasy.client.communication.jsonobject.LoginResponse;
import com.contracteasy.client.communication.jsonobject.SignUpResponse;
import com.contracteasy.client.communication.dto.ServerRequestFactory;
import com.contracteasy.client.session.PageBuilder;
import com.contracteasy.client.session.SessionManager;
import com.contracteasy.client.session.page.ContractsPage;
import com.contracteasy.client.session.page.DashboardPage;
import com.contracteasy.client.utility.Constants;
import com.contracteasy.client.utility.Contract;
import com.google.gwt.core.client.JsonUtils;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.http.client.URL;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootPanel;
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
							case Constants.USER_STATUS_ACTIVE : 
								Window.Location.assign("ContractEasyClient.html?action=dashboard&user=" + loginResponse.getUsid());			
								break;
							case Constants.USER_STATUS_NEW_USER : 
								PageBuilder.load("new");
								break;
							case Constants.USER_STATUS_AWAITING_QUOTE : 
								PageBuilder.load("awaitingQuote");
								break;
							case Constants.USER_STATUS_QUOTE_RECEIVED : 
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
	
	public void countData(final int userId) {
		
		String url = ContractEasyClient.SERVER + "contracts.php";
		
		URL.encode(url);

		builder = new RequestBuilder(RequestBuilder.POST, url);
		builder.setHeader("Content-Type", "application/json");

		try {
			AutoBean<DashboardDTO> dashboardDto = factory.dashboardDto();
			
			dashboardDto.as().setRequest("numActive");
			dashboardDto.as().setUserId(userId);
			
			AutoBean<DashboardDTO> bean = AutoBeanUtils.getAutoBean(dashboardDto.as());
			
			builder.sendRequest(AutoBeanCodex.encode(bean).getPayload(), new RequestCallback() {
				
				@Override
				public void onResponseReceived(Request request, Response response) {
					if (200 == response.getStatusCode()) {
						DashboardResponse dashboardResponse = JsonUtils.<DashboardResponse>safeEval(response.getText());
						DashboardPage page = new DashboardPage(userId,
								dashboardResponse.getContractCount(), 
								dashboardResponse.getNoticeCount(), 
								dashboardResponse.getAlertCount());
						page.build(RootPanel.get("contentContainer"));
					}
				}
				
				@Override
				public void onError(Request request, Throwable exception) {
					// TODO Auto-generated method stub
					
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
			
	}
	
	public void getData(int userId, final String type) {
		
		String url = ContractEasyClient.SERVER + "contracts.php";

		URL.encode(url);

		builder = new RequestBuilder(RequestBuilder.POST, url);
		builder.setHeader("Content-Type", "application/json");
		
		try {
			AutoBean<DataDTO> dataDto = factory.dataDto();
			
			dataDto.as().setRequest("getData");
			dataDto.as().setUserId(userId);
			dataDto.as().setDataType(type);
			
			AutoBean<DataDTO> bean = AutoBeanUtils.getAutoBean(dataDto.as());
			
			builder.sendRequest(AutoBeanCodex.encode(bean).getPayload(), new RequestCallback() {
				
				@Override
				public void onResponseReceived(Request request, Response response) {
					if (200 == response.getStatusCode()) {
						switch (type) {
						case "co" : {
							Window.alert(response.getText());
							ContractsResponse contractsResponse = JsonUtils.<ContractsResponse>safeEval(response.getText());
							List<Contract> contracts = new ArrayList<Contract>();
							
							Window.alert(contractsResponse.getContracts().size() + "");
							
							ContractsPage page = new ContractsPage(contracts);
							page.build(RootPanel.get("contentContainer"));
						}
						}
					}
				}
				
				@Override
				public void onError(Request request, Throwable exception) {
					// TODO Auto-generated method stub
					
				}
			});
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
