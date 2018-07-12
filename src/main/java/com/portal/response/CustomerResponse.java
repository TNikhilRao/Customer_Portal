package com.portal.response;

import org.springframework.stereotype.Component;

import com.portal.dto.CustomerDto;

@Component
public class CustomerResponse extends BaseResponse {

	private CustomerDto customerDetails;

	public CustomerDto getCustomerDetails() {
		return customerDetails;
	}

	public void setCustomerDetails(CustomerDto customerDetails) {
		this.customerDetails = customerDetails;
	}

	@Override
	public String toString() {
		return "CustomerResponse [customerDetails=" + customerDetails + "]";
	}

}
