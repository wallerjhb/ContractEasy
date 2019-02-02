package com.contracteasy.client.communication.dto;

import com.google.web.bindery.autobean.shared.AutoBean;
import com.google.web.bindery.autobean.shared.AutoBeanFactory;

public interface ServerRequestFactory extends AutoBeanFactory{

	AutoBean<RequestDTO> requestDTO();
	AutoBean<UserDTO> userDto();
	AutoBean<DashboardDTO> dashboardDto();
	AutoBean<DataDTO> dataDto();
	AutoBean<DetailsDTO> detailsDto();
	AutoBean<CompanyDetailsDTO> companyDetailsDto();
	AutoBean<BankDetailsDTO> bankDetailsDTO();
	AutoBean<PackageSelectionDTO> packageSelectionDto();
}
