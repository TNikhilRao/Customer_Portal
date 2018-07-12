package com.portal.service;

import org.springframework.stereotype.Service;

import com.portal.dto.CustomerDto;

/**
 * @author Nikhil.Tirmanwar
 *
 */
@Service
public interface ICustomerService {

	/**
	 * Method to save and return Customer details
	 * 
	 * @param dto
	 * @return
	 */
	CustomerDto createCustomer(CustomerDto dto);

	/**
	 * Method to update and return Customer details
	 * 
	 * @param dto
	 * @return
	 */
	CustomerDto updateCustomer(CustomerDto dto);

	/**
	 * Method to delete Customer details by mobile number
	 * 
	 * @param mobileNumber
	 * @return
	 * @throws Exception
	 */
	boolean deleteCustomer(Long mobileNumber);

	/**
	 * Method to retrieve Customer details by mobile number
	 * 
	 * @param mobileNumber
	 * @return
	 * @throws Exception
	 */
	CustomerDto getCustomer(Long mobileNumber);
}
