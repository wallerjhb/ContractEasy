package com.contracteasy.client.communication;

import java.util.ArrayList;

import com.contracteasy.client.client.ContractEasyClient;
import com.contracteasy.client.communication.dto.DashboardDTO;
import com.contracteasy.client.communication.dto.DataDTO;
import com.contracteasy.client.communication.dto.DetailsDTO;
import com.contracteasy.client.communication.dto.ServerRequestFactory;
import com.contracteasy.client.communication.jsonobject.ContractsResponse;
import com.contracteasy.client.communication.jsonobject.DashboardResponse;
import com.contracteasy.client.communication.jsonobject.NoticesResponse;
import com.contracteasy.client.session.page.ContractDetailsPage;
import com.contracteasy.client.session.page.ContractsPage;
import com.contracteasy.client.session.page.DashboardPage;
import com.contracteasy.client.session.page.NoticeDetailsPage;
import com.contracteasy.client.session.page.NoticesPage;
import com.contracteasy.client.utility.Contract;
import com.contracteasy.client.utility.Notice;
import com.google.gwt.core.client.JsonUtils;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
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

public class ContractsServerCaller extends ServerCaller {
	
	public static ContractsServerCaller instance;

	private RequestBuilder builder;
	private ServerRequestFactory factory = GWT.create(ServerRequestFactory.class);
	
	private ContractsServerCaller() {}
	
	public static ContractsServerCaller getInstance() {
		if (instance == null) instance = new ContractsServerCaller();
		return instance;
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

							ArrayList<Contract> contracts = new ArrayList<Contract>();

							JSONValue jsonValue;
							JSONArray jsonArray;
							JSONObject jsonObject;
							jsonValue = JSONParser.parseStrict(response.getText());

							if ((jsonObject = jsonValue.isObject()) == null) {
								Window.alert("Error parsing the JSON 1");
							}

							jsonValue = jsonObject.get("contracts"); 
							if ((jsonArray = jsonValue.isArray()) == null) {
								Window.alert("Error parsing the JSON 2");
							}

							try {
								for (int i=0; i<jsonArray.size(); i++) {
									jsonValue = jsonArray.get(i);
									Contract contract = new Contract();
									contract.setId(stringOrNull(jsonValue.isObject().get("id").isString()));
									contract.setStatus(stringOrNull(jsonValue.isObject().get("status").isString()));
									contract.setDescription(stringOrNull(jsonValue.isObject().get("desc").isString()));
									contract.setReference(stringOrNull(jsonValue.isObject().get("reference").isString()));
									contract.setClientRef(stringOrNull(jsonValue.isObject().get("clientref").isString()));
									contract.setType(stringOrNull(jsonValue.isObject().get("type").isString()));
									contract.setCounterParty(stringOrNull(jsonValue.isObject().get("counterparty").isString()));
									contract.setNoticePeriod(stringOrNull(jsonValue.isObject().get("noticeperioddays").isString()));
									contract.setStartDate(stringOrNull(jsonValue.isObject().get("start").isString()));
									contract.setTerminationDate(stringOrNull(jsonValue.isObject().get("termination").isString()));
									contract.setEscalationDate(stringOrNull(jsonValue.isObject().get("escalation").isString()));
									contract.setRenewalDate(stringOrNull(jsonValue.isObject().get("renewal").isString()));
									contracts.add(contract);
								}
							} catch (Exception e) {
								Window.alert(e.getMessage());
							}

							ContractsPage page = new ContractsPage(contracts);
							page.build(RootPanel.get("contentContainer"));
						}

						break;

						case "no" : {

							ArrayList<Notice> notices = new ArrayList<Notice>();

							JSONValue jsonValue;
							JSONArray jsonArray;
							JSONObject jsonObject;
							jsonValue = JSONParser.parseStrict(response.getText());

							if ((jsonObject = jsonValue.isObject()) == null) {
								Window.alert("Error parsing the JSON 1");
							}

							jsonValue = jsonObject.get("notices"); 
							if ((jsonArray = jsonValue.isArray()) == null) {
								Window.alert("Error parsing the JSON 2");
							}

							try {
								for (int i=0; i<jsonArray.size(); i++) {
									jsonValue = jsonArray.get(i);
									Notice notice = new Notice();
									notice.setId(stringOrNull(jsonValue.isObject().get("id").isString()));
									notice.setStatus(stringOrNull(jsonValue.isObject().get("status").isString()));
									notice.setDescription(stringOrNull(jsonValue.isObject().get("desc").isString()));
									notice.setReference(stringOrNull(jsonValue.isObject().get("reference").isString()));
									notice.setDateSent(stringOrNull(jsonValue.isObject().get("datesent").isString()));
									notice.setCounterParty(stringOrNull(jsonValue.isObject().get("counterparty").isString()));
									notices.add(notice);
								}
							} catch (Exception e) {
								Window.alert(e.getMessage());
							}

							NoticesPage page = new NoticesPage(notices);
							page.build(RootPanel.get("contentContainer"));
						}

						break;

						case "al" : {

						}

						break;

						default : break;
						}
					}
				}

				@Override
				public void onError(Request request, Throwable exception) {
					// TODO Auto-generated method stub

				}
			});

		} catch (Exception e) {
			Window.alert(e.getMessage());
		}
	}

	public void getDetails(int id, final String type) {
		String url = ContractEasyClient.SERVER + "contracts.php";

		URL.encode(url);

		builder = new RequestBuilder(RequestBuilder.POST, url);
		builder.setHeader("Content-Type", "application/json");

		try {
			AutoBean<DetailsDTO> detailsDto = factory.detailsDto();

			detailsDto.as().setRequest("getDetails");
			detailsDto.as().setId(id);
			detailsDto.as().setDataType(type);

			AutoBean<DetailsDTO> bean = AutoBeanUtils.getAutoBean(detailsDto.as());

			builder.sendRequest(AutoBeanCodex.encode(bean).getPayload(), new RequestCallback() {

				@Override
				public void onResponseReceived(Request request, Response response) {
					Window.alert(response.getText());
					try {
						if (200 == response.getStatusCode()) {
							switch (type) {
							case "co": {
								ContractsResponse contractsResponse = JsonUtils.<ContractsResponse>safeEval(response.getText());
								Contract contract = new Contract();
								contract.setId(contractsResponse.getId());
								contract.setDescription(contractsResponse.getDesc());
								contract.setType(contractsResponse.getType());
								contract.setReference(contractsResponse.getReference());
								contract.setClientRef(contractsResponse.getClientRef());
								contract.setCounterParty(contractsResponse.getCounterParty());
								contract.setStartDate(contractsResponse.getStart());
								contract.setTerminationDate(contractsResponse.getTermination());
								contract.setEscalationDate(contractsResponse.getEscalation());
								contract.setRenewalDate(contractsResponse.getRenewal());
								contract.setNoticePeriod(contractsResponse.getNoticePeriod());

								ContractDetailsPage page = new ContractDetailsPage(contract);
								page.build(RootPanel.get("contentContainer"));
							}
							break;

							case "no": {
								NoticesResponse noticesResponse = JsonUtils.<NoticesResponse>safeEval(response.getText());
								Notice notice = new Notice();
								notice.setId(noticesResponse.getId());
								notice.setStatus(noticesResponse.getStatus());
								notice.setDescription(noticesResponse.getDesc());
								notice.setReference(noticesResponse.getReference());

								NoticeDetailsPage page = new NoticeDetailsPage(notice);
							}

							break;

							case "al": {

							}

							break;

							default:
								break;
							}
						}
					} catch (Exception e) {
						Window.alert("Data reponse error " + e.getMessage());
					}
				}

				@Override
				public void onError(Request request, Throwable exception) {

				}
			});

		} catch (Exception e) {
			Window.alert(e.getMessage());
		}
	}

}
