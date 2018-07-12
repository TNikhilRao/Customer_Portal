package com.portal.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.portal.dto.CustomerDto;
import com.portal.response.CustomerResponse;
import com.portal.service.ICustomerService;
import com.portal.util.CustomerValidator;
import com.portal.util.ResultCode;

@RestController
@RequestMapping(value = "/customer")
public class CustomerPortalController {

	private static final Logger logger = LoggerFactory.getLogger(CustomerPortalController.class);

	@Autowired
	ICustomerService customerService;

	@Autowired
	CustomerValidator customerValidator;

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CustomerResponse> createCustomer(@RequestBody CustomerDto customerDto)
			throws IllegalAccessException {

		logger.debug("createCustomer Start ,  \n Request is : ", customerDto);

		boolean isValidRequest = customerValidator.validateCustomerRequest(customerDto);

		CustomerDto createdCustomer = new CustomerDto();
		CustomerResponse customerResponse = new CustomerResponse();

		if (isValidRequest) {
			createdCustomer = customerService.createCustomer(customerDto);
		}

		if (createdCustomer.getCustomerId() != null) {
			customerResponse.setCustomerDetails(createdCustomer);
			customerResponse.setCode(ResultCode.SUCCESS_ADD_CUSTOMER.getCode());
			customerResponse.setStatus(ResultCode.SUCCESS_ADD_CUSTOMER.getStatus());
			customerResponse.setMessage(ResultCode.SUCCESS_ADD_CUSTOMER.getMessage());
			return new ResponseEntity<CustomerResponse>(customerResponse, HttpStatus.CREATED);
		} else {
			customerResponse.setCode(ResultCode.FAILURE_ADD_CUSTOMER.getCode());
			customerResponse.setStatus(ResultCode.FAILURE_ADD_CUSTOMER.getStatus());
			customerResponse.setMessage(ResultCode.FAILURE_ADD_CUSTOMER.getMessage());
			return new ResponseEntity<CustomerResponse>(customerResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@GetMapping(value = "/{mobileNumber}/")
	public ResponseEntity<CustomerResponse> getCustomer(@PathVariable("mobileNumber") Long mobileNumber)
			throws Exception {

		logger.debug("getCustomer Start ,  \n for Mobile Number : ", mobileNumber);

		CustomerDto retrievedCustomerDto = customerService.getCustomer(mobileNumber);
		CustomerResponse customerResponse = new CustomerResponse();

		if (retrievedCustomerDto.getCustomerId() != null) {
			customerResponse.setCustomerDetails(retrievedCustomerDto);
			customerResponse.setCode(ResultCode.SUCCESS_GET_CUSTOMER.getCode());
			customerResponse.setStatus(ResultCode.SUCCESS_GET_CUSTOMER.getStatus());
			customerResponse.setMessage(ResultCode.SUCCESS_GET_CUSTOMER.getMessage());
			return new ResponseEntity<CustomerResponse>(customerResponse, HttpStatus.OK);
		} else {
			customerResponse.setCode(ResultCode.FAILURE_GET_CUSTOMER.getCode());
			customerResponse.setStatus(ResultCode.FAILURE_GET_CUSTOMER.getStatus());
			customerResponse.setMessage(ResultCode.FAILURE_GET_CUSTOMER.getMessage());
			return new ResponseEntity<CustomerResponse>(customerResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CustomerResponse> updateCustomer(@RequestBody CustomerDto customerDto) {

		logger.debug("updateCustomer Start ,  \n request is : ", customerDto);

		// boolean isValidRequest =
		// customerValidator.validateCustomerRequest(customerDto);

		boolean isValidRequest = customerValidator.validateCustomerUpdateRequest(customerDto);
		CustomerResponse customerResponse = new CustomerResponse();
		CustomerDto updatedCustomer = new CustomerDto();
		if (isValidRequest) {
			updatedCustomer = customerService.updateCustomer(customerDto);
		}

		if (updatedCustomer != null) {
			customerResponse.setCustomerDetails(updatedCustomer);
			customerResponse.setCode(ResultCode.SUCCESS_UPDATE_CUSTOMER.getCode());
			customerResponse.setStatus(ResultCode.SUCCESS_UPDATE_CUSTOMER.getStatus());
			customerResponse.setMessage(ResultCode.SUCCESS_UPDATE_CUSTOMER.getMessage());
			return new ResponseEntity<CustomerResponse>(customerResponse, HttpStatus.OK);
		} else {
			customerResponse.setCode(ResultCode.FAILURE_UPDATE_CUSTOMER.getCode());
			customerResponse.setStatus(ResultCode.FAILURE_UPDATE_CUSTOMER.getStatus());
			customerResponse.setMessage(ResultCode.FAILURE_UPDATE_CUSTOMER.getMessage());
			return new ResponseEntity<CustomerResponse>(customerResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@DeleteMapping(value = "/{mobileNumber}/")
	public ResponseEntity<CustomerResponse> deleteCustomer(@PathVariable("mobileNumber") Long mobileNumber)
			throws Exception {

		logger.debug("deleteCustomer Start ,  \n for Mobile Number : ", mobileNumber);

		boolean isCustomerDeleted = customerService.deleteCustomer(mobileNumber);
		CustomerResponse customerResponse = new CustomerResponse();

		if (isCustomerDeleted) {
			customerResponse.setCode(ResultCode.SUCCESS_DELETE_CUSTOMER.getCode());
			customerResponse.setStatus(ResultCode.SUCCESS_DELETE_CUSTOMER.getStatus());
			customerResponse.setMessage(ResultCode.SUCCESS_DELETE_CUSTOMER.getMessage());
			return new ResponseEntity<CustomerResponse>(customerResponse, HttpStatus.OK);
		} else {
			customerResponse.setCode(ResultCode.FAILURE_DELETE_CUSTOMER.getCode());
			customerResponse.setStatus(ResultCode.FAILURE_DELETE_CUSTOMER.getStatus());
			customerResponse.setMessage(ResultCode.FAILURE_DELETE_CUSTOMER.getMessage());
			return new ResponseEntity<CustomerResponse>(customerResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
}
