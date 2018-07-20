package com.portal.tests;

import static org.junit.Assert.assertEquals;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

import com.codahale.metrics.MetricRegistry;
import com.portal.dao.ICustomerDao;
import com.portal.dto.CustomerDto;
import com.portal.entity.Customer;
import com.portal.exception.CustomerNotFoundException;
import com.portal.service.impl.CustomerServiceImpl;
import com.portal.util.ConversionUtil;
import com.portal.util.CustomerValidator;

@RunWith(SpringRunner.class)
public class CustomerServiceTest {

	@InjectMocks
	CustomerServiceImpl customerServiceImpl;

	@Mock
	ICustomerDao customerDao;

	@Mock
	ConversionUtil conversionUtil;

	@Mock
	CustomerValidator customerValidator;

	@Before
	public void setup() {
		ReflectionTestUtils.setField(this.customerServiceImpl, "metricRegistry", new MetricRegistry());
		this.customerServiceImpl.ready();
	}

	@Test
	public void createCustomerTestForSuccess() {
		CustomerDto CustomerDtoAddRequest = new CustomerDto();
		CustomerDtoAddRequest.setCustomerId(1);
		CustomerDtoAddRequest.setFirstName("firstNameTest");
		CustomerDtoAddRequest.setLastName("LastNameTest");
		CustomerDtoAddRequest.setEmail("email@test.com");
		CustomerDtoAddRequest.setMobileNumber(Long.valueOf("1234567890"));

		Customer customer = getCustomerEntity();
		Mockito.when(customerDao.save(Mockito.any())).thenReturn(customer);
		Mockito.when(conversionUtil.getCustomerDto(Mockito.any())).thenReturn(CustomerDtoAddRequest);
		CustomerDto customerDto = customerServiceImpl.createCustomer(CustomerDtoAddRequest);

		assertEquals(customerDto.getCustomerId(), CustomerDtoAddRequest.getCustomerId());

	}

	@Test
	public void createCustomerTestForFail() {
		CustomerDto CustomerDtoAddRequest = new CustomerDto();
		CustomerDtoAddRequest.setCustomerId(1);
		CustomerDtoAddRequest.setFirstName("firstNameTest");
		CustomerDtoAddRequest.setLastName("LastNameTest");
		CustomerDtoAddRequest.setEmail("email@test.com");
		CustomerDtoAddRequest.setMobileNumber(Long.valueOf("1234567890"));

		Mockito.when(customerDao.save(Mockito.any())).thenReturn(null);

		customerServiceImpl.createCustomer(CustomerDtoAddRequest);

	}

	@Test
	public void getCustomerTestForSuccess() {
		Long mobileNumber = Long.valueOf("1234567890");

		Customer customer = getCustomerEntity();
		CustomerDto customerDto = getCustomerDto();

		Mockito.when(customerValidator.validateMobileNumber(mobileNumber)).thenReturn(true);
		Mockito.when(customerDao.findByMobileNumber(mobileNumber)).thenReturn(customer);

		Mockito.when(conversionUtil.getCustomerDto(customer)).thenReturn(customerDto);
		customerDto = customerServiceImpl.getCustomer(mobileNumber);

		assertEquals(customerDto.getCustomerId(), customer.getCustomerId());
	}

	@Test(expected = CustomerNotFoundException.class)
	public void getCustomer_CustomerNotFoundExceptionTest() {
		Long mobileNumber = Long.valueOf("1234567890");

		Mockito.when(customerValidator.validateMobileNumber(mobileNumber)).thenReturn(true);
		Mockito.when(customerDao.findByMobileNumber(mobileNumber)).thenReturn(null);
		Mockito.when(customerServiceImpl.getCustomer(mobileNumber)).thenThrow(
				new CustomerNotFoundException(Mockito.anyString(), Mockito.anyString(), Mockito.anyString()));
	}

	@Test(expected = CustomerNotFoundException.class)
	public void deleteCustomer_CustomerNotFoundExceptionTest() {

		Long mobileNumber = new Long(1234567888);
		Mockito.when(customerValidator.validateMobileNumber(mobileNumber)).thenReturn(true);
		Mockito.when(customerDao.findByMobileNumber(mobileNumber)).thenReturn(null);

		Mockito.when(customerServiceImpl.deleteCustomer(mobileNumber)).thenThrow(
				new CustomerNotFoundException(Mockito.anyString(), Mockito.anyString(), Mockito.anyString()));

	}

	@Test
	public void deleteCustomerTestForSuccess() {

		Long mobileNumber = new Long(1234567890);
		Customer customer = getCustomerEntity();
		Mockito.when(customerValidator.validateMobileNumber(mobileNumber)).thenReturn(true);
		Mockito.when(customerDao.findByMobileNumber(mobileNumber)).thenReturn(customer);
		Mockito.when(customerDao.findById(customer.getCustomerId())).thenReturn(Optional.of(customer));
		customerServiceImpl.deleteCustomer(mobileNumber);
		Mockito.when(customerDao.findByMobileNumber(mobileNumber)).thenReturn(null);

	}

	private CustomerDto getCustomerDto() {
		CustomerDto customerDto = new CustomerDto();
		customerDto.setCustomerId(1);
		customerDto.setFirstName("firstNameTest");
		customerDto.setLastName("LastNameTest");
		customerDto.setEmail("email@test.com");
		customerDto.setMobileNumber(Long.valueOf("1234567890"));

		return customerDto;
	}

	private Customer getCustomerEntity() {

		Customer customer = new Customer();
		customer.setCustomerId(1);
		customer.setFirstName("firstNameTest");
		customer.setLastName("LastNameTest");
		customer.setEmail("email@test.com");
		customer.setMobileNumber(Long.valueOf("1234567890"));

		return customer;
	}

	@Test
	public void updateCustomerTestForSuccess() {

		CustomerDto dto = getCustomerDto();
		Customer customer = getCustomerEntity();
		Mockito.when(conversionUtil.getCustomerEntity(dto)).thenReturn(customer);
		Mockito.when(customerDao.findByMobileNumber(Long.valueOf("1234567890"))).thenReturn(customer);
		Mockito.when(customerDao.findById(1)).thenReturn(Optional.of(customer));

		customer.setFirstName("upFirstName");
		customer.setLastName("upLastName");
		Mockito.when(customerDao.save(customer)).thenReturn(customer);
		dto.setFirstName("upFirstName");
		dto.setLastName("upLastName");
		Mockito.when(conversionUtil.getCustomerDto(customer)).thenReturn(dto);
		CustomerDto updatedDto = customerServiceImpl.updateCustomer(dto);
		assertEquals(dto.getFirstName(), updatedDto.getFirstName());

	}

	@Test(expected = CustomerNotFoundException.class)
	public void updateCustomerTestForFail() {

		CustomerDto dto = getCustomerDto();
		Customer customer = getCustomerEntity();

		Mockito.when(conversionUtil.getCustomerEntity(dto)).thenReturn(customer);
		Mockito.when(customerDao.findByMobileNumber(Long.valueOf("1234567890"))).thenReturn(null);

		Mockito.when(customerServiceImpl.updateCustomer(dto)).thenThrow(
				new CustomerNotFoundException(Mockito.anyString(), Mockito.anyString(), Mockito.anyString()));

	}

}
