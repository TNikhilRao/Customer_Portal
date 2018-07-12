package com.portal.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.portal.entity.Customer;

@Repository
public interface ICustomerDao extends CrudRepository<Customer, Integer> {

	/**
	 * Database call to find customer details by MobileNumber
	 * 
	 * @param mobileNumber
	 * @return
	 */
	public Customer findByMobileNumber(Long mobileNumber);

	/**
	 * Database call to delete customer details by MobileNumber
	 * 
	 * @param mobileNumber
	 * @return
	 */
	public boolean deleteByMobileNumber(Long mobileNumber);

	/**
	 * Database call to check existence of customer by email
	 * 
	 * @param email
	 * @return
	 */
	public boolean existsByEmail(String email);

	/**
	 * Database call to check existence of customer by mobileNumber
	 * 
	 * @param mobileNumber
	 * @return
	 */
	public boolean existsByMobileNumber(Long mobileNumber);
	
	
	/**
	 * Database call to get Customer details by mobileNumber and email
	 * @param mobileNumber
	 * @param email
	 * @return
	 */
	public Customer findByMobileNumberAndEmail(Long mobileNumber, String email);

}
