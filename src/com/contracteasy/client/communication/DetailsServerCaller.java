package com.contracteasy.client.communication;

import java.util.ArrayList;

import com.contracteasy.client.client.ContractEasyClient;
import com.contracteasy.client.communication.dto.BankDetailsDTO;
import com.contracteasy.client.communication.dto.CompanyDetailsDTO;
import com.contracteasy.client.communication.dto.PackageSelectionDTO;
import com.contracteasy.client.communication.dto.RequestDTO;
import com.contracteasy.client.communication.dto.ServerRequestFactory;
import com.contracteasy.client.session.PageBuilder;
import com.contracteasy.client.session.page.PackageSelectionPage;
import com.contracteasy.client.utility.BankDetails;
import com.contracteasy.client.utility.CompanyDetails;
import com.contracteasy.client.utility.ContractPackage;
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
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.web.bindery.autobean.shared.AutoBean;
import com.google.web.bindery.autobean.shared.AutoBeanCodex;
import com.google.web.bindery.autobean.shared.AutoBeanUtils;

public class DetailsServerCaller extends ServerCaller{
	
	private static DetailsServerCaller instance = null;
	
	private RequestBuilder builder;
	private ServerRequestFactory factory = GWT.create(ServerRequestFactory.class);
	
	private DetailsServerCaller() {}

	public static DetailsServerCaller getInstance() {
		if (instance == null) instance = new DetailsServerCaller();
		return instance;
	}
	
	public void submitCompanyDetails(final CompanyDetails details) {

		Window.alert("Uploading company details");

		String url = ContractEasyClient.SERVER + "access.php";

		URL.encode(url);

		builder = new RequestBuilder(RequestBuilder.POST, url);
		builder.setHeader("Content-Type", "application/json");

		try {
			
			AutoBean<CompanyDetailsDTO> companyDetailsDto = factory.companyDetailsDto();

			companyDetailsDto.as().setRequest("submitCompanyDetails");
			companyDetailsDto.as().setUser(Integer.toString(details.getUser()));
			companyDetailsDto.as().setCompanyName(details.getCompanyName());
			companyDetailsDto.as().setContactName(details.getContactName());
			companyDetailsDto.as().setEmail(details.getEmail());
			companyDetailsDto.as().setPhysicalAddress(details.getPhysicalAddress());

			AutoBean<CompanyDetailsDTO> bean = AutoBeanUtils.getAutoBean(companyDetailsDto.as());

			builder.sendRequest(AutoBeanCodex.encode(bean).getPayload(), new RequestCallback() {

				@Override
				public void onResponseReceived(Request request, Response response) {
					if (200 == response.getStatusCode()) {
						loadPackageOptions(details.getUser());
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
	}
	
	public void loadPackageOptions(final int user) {

		Window.alert("Loading packages for user " + user);

		String url = ContractEasyClient.SERVER + "contracts.php";

		URL.encode(url);

		builder = new RequestBuilder(RequestBuilder.POST, url);
		builder.setHeader("Content-Type", "application/json");

		try {
			AutoBean<RequestDTO> dataDto = factory.requestDTO();

			dataDto.as().setRequest("getPackages");

			AutoBean<RequestDTO> bean = AutoBeanUtils.getAutoBean(dataDto.as());

			builder.sendRequest(AutoBeanCodex.encode(bean).getPayload(), new RequestCallback() {

				@Override
				public void onResponseReceived(Request request, Response response) {
					Window.alert("Response recieved with code " + response.getStatusCode() + " - " + response.getText());
					if (200 == response.getStatusCode()) {

						ArrayList<ContractPackage> packages = new ArrayList<ContractPackage>();

						JSONValue jsonValue;
						JSONArray jsonArray;
						JSONObject jsonObject;
						jsonValue = JSONParser.parseStrict(response.getText());

						if ((jsonObject = jsonValue.isObject()) == null) {
							Window.alert("Error parsing the JSON 1");
						}

						jsonValue = jsonObject.get("pkg"); 
						if ((jsonArray = jsonValue.isArray()) == null) {
							Window.alert("Error parsing the JSON 2");
						}

						try {
							for (int i=0; i<jsonArray.size(); i++) {
								jsonValue = jsonArray.get(i);
								ContractPackage p = new ContractPackage();
								p.setId(stringOrNull(jsonValue.isObject().get("id").isString()));
								p.setName(stringOrNull(jsonValue.isObject().get("name").isString()));
								p.setMax(stringOrNull(jsonValue.isObject().get("max").isString()));
								packages.add(p);
							}
						} catch (Exception e) {
							Window.alert(e.getMessage());
						}

						Window.alert(Integer.toString(packages.size()));

						PackageSelectionPage page = new PackageSelectionPage(packages, user);
						page.build(RootPanel.get("contentContainer"));
					}

				}

				@Override
				public void onError(Request request, Throwable exception) {
					Window.alert("Error fetching packages");
				}
			});
		} catch (Exception e) {
			Window.alert(e.getLocalizedMessage());
		}

	}
	
	public void uploadBankDetails(final BankDetails bankDetails) {
		String url = ContractEasyClient.SERVER + "access.php";

		URL.encode(url);

		builder = new RequestBuilder(RequestBuilder.POST, url);
		builder.setHeader("Content-Type", "application/json");
		
		try {
			AutoBean<BankDetailsDTO> details = factory.bankDetailsDTO();
			details.as().setRequest("uploadBankDetails");
			details.as().setUser(bankDetails.getUser());
			details.as().setAccountNum(bankDetails.getAccountNum());
			details.as().setBranch(bankDetails.getBranch());
			details.as().setAccountName(bankDetails.getAccountName());
			
			AutoBean<BankDetailsDTO> bean = AutoBeanUtils.getAutoBean(details.as());
			
			builder.sendRequest(AutoBeanCodex.encode(bean).getPayload(), new RequestCallback() {
				
				@Override
				public void onResponseReceived(Request request, Response response) {
					PageBuilder.load("dashboard", Integer.parseInt(bankDetails.getUser()));
				}
				
				@Override
				public void onError(Request request, Throwable exception) {
					// TODO Auto-generated method stub
					
				}
			});
			
		} catch (RequestException e) {
			e.printStackTrace();
		}
	}
	
	public void selectPackage(final int user, int packageChoice) {
		
		String url = ContractEasyClient.SERVER + "access.php";

		URL.encode(url);

		builder = new RequestBuilder(RequestBuilder.POST, url);
		builder.setHeader("Content-Type", "application/json");
		
		try {
			AutoBean<PackageSelectionDTO> selection = factory.packageSelectionDto();
			selection.as().setRequest("selectPackage");
			selection.as().setUser(Integer.toString(user));
			selection.as().setPkg(Integer.toString(packageChoice));
			
			AutoBean<PackageSelectionDTO> bean = AutoBeanUtils.getAutoBean(selection.as());
			
			builder.sendRequest(AutoBeanCodex.encode(bean).getPayload(), new RequestCallback() {
				
				@Override
				public void onResponseReceived(Request request, Response response) {
					PageBuilder.load("bankDetails", user);
				}
				
				@Override
				public void onError(Request request, Throwable exception) {
					// TODO Auto-generated method stub
					
				}
			});
			
		} catch (RequestException e) {
			e.printStackTrace();
		}
	}
}
