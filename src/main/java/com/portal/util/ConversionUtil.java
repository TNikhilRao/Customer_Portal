package com.portal.util;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.portal.dto.CustomerDto;
import com.portal.entity.Customer;
import com.portal.exception.CustomerCreationException;

/**
 * @author Nikhil.Tirmanwar
 *
 */
@Component
public class ConversionUtil {

	@Autowired
	static ModelMapper toEntity;

	@Autowired
	static ModelMapper toDto;

	/**
	 * Method to convert DTO into Entity
	 * 
	 * @param customerDto
	 * @return
	 */
	public Customer getCustomerEntity(CustomerDto customerDto) {

		/*
		 * Customer entity = new Customer();
		 * 
		 * if (customerDto.getCustomerId() != null) {
		 * entity.setCustomerId(customerDto.getCustomerId()); } entity =
		 * toEntity.map(customerDto, Customer.class); return entity;
		 */

		Customer entity = new Customer();

		if (customerDto.getCustomerId() != null) {
			entity.setCustomerId(customerDto.getCustomerId());
		}
		entity.setFirstName(customerDto.getFirstName());
		entity.setLastName(customerDto.getLastName());
		entity.setEmail(customerDto.getEmail());
		entity.setMobileNumber(customerDto.getMobileNumber());
		return entity;
	}

	/**
	 * Method to convert Entity into DTO
	 * 
	 * @param customerEntity
	 * @return
	 */
	public CustomerDto getCustomerDto(Customer customerEntity) {

		/*
		 * CustomerDto dto = new CustomerDto(); dto = toDto.map(customerEntity,
		 * CustomerDto.class); if (customerEntity.getCustomerId() != null) {
		 * dto.setCustomerId(customerEntity.getCustomerId()); } return dto;
		 */

		CustomerDto dto = new CustomerDto();

		if (customerEntity != null) {
			dto.setCustomerId(customerEntity.getCustomerId());
			dto.setFirstName(customerEntity.getFirstName());
			dto.setLastName(customerEntity.getLastName());
			dto.setEmail(customerEntity.getEmail());
			dto.setMobileNumber(customerEntity.getMobileNumber());
			return dto;
		} else {
			ResultCode error = ResultCode.INVALID_CUSTOMER_PROFILE;
			throw new CustomerCreationException(error.getCode(), error.getStatus(), error.getMessage());
		}

	}

}
