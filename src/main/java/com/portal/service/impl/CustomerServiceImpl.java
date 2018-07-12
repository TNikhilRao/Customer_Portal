package com.portal.service.impl;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Timer;
import com.portal.dao.ICustomerDao;
import com.portal.dto.CustomerDto;
import com.portal.entity.Customer;
import com.portal.exception.CustomerNotFoundException;
import com.portal.service.ICustomerService;
import com.portal.util.ConversionUtil;
import com.portal.util.CustomerValidator;
import com.portal.util.ResultCode;

/**
 * @author Nikhil.Tirmanwar
 *
 */
@Service
public class CustomerServiceImpl implements ICustomerService {

	private static final Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);

	@Autowired
	ICustomerDao customerDao;

	@Autowired
	private MetricRegistry metricRegistry;

	@Autowired
	private ConversionUtil conversionUtil;

	@Autowired
	private CustomerValidator customerValidator;

	private Timer createCustomerTimer;
	private Timer getCustomerTimer;
	private Timer updateCustomerTimer;
	private Timer deleteCustomerTimer;

	@PostConstruct
	public void ready() {
		createCustomerTimer = metricRegistry.timer(MetricRegistry.name(this.getClass(), "createCustomer"));
		updateCustomerTimer = metricRegistry.timer(MetricRegistry.name(this.getClass(), "updateCustomer"));
		getCustomerTimer = metricRegistry.timer(MetricRegistry.name(this.getClass(), "getCustomer"));
		deleteCustomerTimer = metricRegistry.timer(MetricRegistry.name(this.getClass(), "deleteCustomer"));
		logger.info("ready - metricRegistry initialized successfully");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.portal.service.ICustomerService#createCustomer(com.portal.dto.
	 * CustomerDto)
	 */
	@Override
	public CustomerDto createCustomer(CustomerDto dto) {

		logger.debug("createCustomer Start ,  \n request : ", dto);

		long startTime = System.currentTimeMillis();

		Timer.Context context = null;

		Customer customer = conversionUtil.getCustomerEntity(dto);

		context = createCustomerTimer.time();
		Customer savedCustomer = customerDao.save(customer);
		context.stop();
		CustomerDto savedCustomerDto = conversionUtil.getCustomerDto(savedCustomer);

		long endTime = System.currentTimeMillis();
		logger.info("PERFORMANCE: createCustomer - Customer added - Total execution time = {} ms.",
				(endTime - startTime));

		logger.debug("createCustomer End");

		return savedCustomerDto;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.portal.service.ICustomerService#updateCustomer(com.portal.dto.
	 * CustomerDto)
	 */
	@Override
	public CustomerDto updateCustomer(CustomerDto dto) {

		logger.debug("updateCustomer Start ,  \n request : ", dto);

		long startTime = System.currentTimeMillis();

		Timer.Context context = null;

		Customer customer = conversionUtil.getCustomerEntity(dto);
		Customer customerToUpdate = new Customer();
		Customer existingCustomer = customerDao.findByMobileNumber(dto.getMobileNumber());
		Customer updatedCustomer = new Customer();
		if (existingCustomer != null) {
			customerToUpdate = customerDao.findById(existingCustomer.getCustomerId()).get();
			customerToUpdate.setFirstName(customer.getFirstName());
			customerToUpdate.setLastName(customer.getLastName());
			context = updateCustomerTimer.time();
			updatedCustomer = customerDao.save(customerToUpdate);
			context.stop();
			CustomerDto updatedDto = conversionUtil.getCustomerDto(updatedCustomer);
			long endTime = System.currentTimeMillis();
			logger.info("PERFORMANCE: updateCustomer - Customer updated - Total execution time = {} ms.",
					(endTime - startTime));

			logger.debug("updateCustomer End");
			return updatedDto;
		} else {
			ResultCode error = ResultCode.INVALID_CUSTOMER_PROFILE;
			throw new CustomerNotFoundException(error.getCode(), error.getStatus(), error.getMessage());
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.portal.service.ICustomerService#deleteCustomer(java.lang.Long)
	 */
	@Override
	public boolean deleteCustomer(Long mobileNumber) {

		logger.debug("deleteCustomer Start ,  \n for Mobile Number : ", mobileNumber);
		long startTime = System.currentTimeMillis();

		Timer.Context context = null;

		boolean isMobileNumberValid = customerValidator.validateMobileNumber(mobileNumber);

		boolean isDeleted = false;
		if (isMobileNumberValid) {

			Customer cust = customerDao.findByMobileNumber(mobileNumber);
			Customer customerToDelete;
			if (cust != null) {
				customerToDelete = customerDao.findById(cust.getCustomerId()).get();
			} else {
				ResultCode error = ResultCode.INVALID_CUSTOMER_PROFILE;
				throw new CustomerNotFoundException(error.getCode(), error.getStatus(), error.getMessage());
			}
			context = deleteCustomerTimer.time();
			customerDao.delete(customerToDelete);
			context.stop();
			long endTime = System.currentTimeMillis();
			logger.info("PERFORMANCE: deleteCustomer - Customer deleted - Total execution time = {} ms.",
					(endTime - startTime));

			Customer verifyCustomerDeleted = customerDao.findByMobileNumber(mobileNumber);

			if (verifyCustomerDeleted == null) {
				isDeleted = true;
			}

		}
		logger.debug("deleteCustomer End");
		return isDeleted;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.portal.service.ICustomerService#getCustomer(java.lang.Long)
	 */
	@Override
	public CustomerDto getCustomer(Long mobileNumber) {

		logger.debug("getCustomer Start ,  \n for Mobile Number : ", mobileNumber);
		long startTime = System.currentTimeMillis();
		Timer.Context context = null;

		boolean isMobileNumberValid = customerValidator.validateMobileNumber(mobileNumber);
		Customer customer = null;
		CustomerDto customerDto = new CustomerDto();
		context = getCustomerTimer.time();
		if (isMobileNumberValid) {
			customer = customerDao.findByMobileNumber(mobileNumber);
			context.stop();
		}
		long endTime = System.currentTimeMillis();
		logger.info("PERFORMANCE: getCustomer - Customer retrieved - Total execution time = {} ms.",
				(endTime - startTime));
		if (customer != null) {
			customerDto = conversionUtil.getCustomerDto(customer);
		} else {
			logger.error("Invalid Customer mobile number : {}", mobileNumber);
			ResultCode error = ResultCode.INVALID_CUSTOMER_PROFILE;
			throw new CustomerNotFoundException(error.getCode(), error.getStatus(), error.getMessage());
		}

		logger.debug("getCustomer End");

		return customerDto;
	}

}
