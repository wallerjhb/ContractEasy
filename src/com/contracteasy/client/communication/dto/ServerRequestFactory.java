package com.contracteasy.client.communication.dto;

import com.google.web.bindery.autobean.shared.AutoBean;
import com.google.web.bindery.autobean.shared.AutoBeanFactory;

public interface ServerRequestFactory extends AutoBeanFactory{

	AutoBean<UserDTO> userDto();
}
