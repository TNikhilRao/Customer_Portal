package com.portal.tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

import com.portal.dto.CustomerDto;
import com.portal.entity.Customer;
import com.portal.util.ConversionUtil;

@RunWith(SpringRunner.class)
public class ConversionUtilTest {

	@Mock
	ConversionUtil conversionUtil;

	Customer customer = new Customer();
	CustomerDto customerDto = new CustomerDto();

	@Before
	public void setUp() {
		customer = getCustomerEntity();
		customerDto = getCustomerDto();
	}

	@Test
	public void getCustomerEntityTest() {

		Mockito.when(conversionUtil.getCustomerEntity(customerDto)).thenReturn(customer);
		assertEquals(customer.getCustomerId(), customerDto.getCustomerId());

	}

	@Test
	public void getCustomerEntityTestForFail() {

		customerDto.setCustomerId(2);
		Mockito.when(conversionUtil.getCustomerEntity(customerDto)).thenReturn(customer);
	}

	@Test
	public void getCustomerDtoTest() {

		Mockito.when(conversionUtil.getCustomerDto(customer)).thenReturn(customerDto);
	}

	@Test
	public void getCustomerDtoTestForFail() {

		Mockito.when(conversionUtil.getCustomerDto(customer)).thenReturn(customerDto);
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

	private CustomerDto getCustomerDto() {
		CustomerDto customerDto = new CustomerDto();
		customerDto.setCustomerId(1);
		customerDto.setFirstName("firstNameTest");
		customerDto.setLastName("LastNameTest");
		customerDto.setEmail("email@test.com");
		customerDto.setMobileNumber(Long.valueOf("1234567890"));

		return customerDto;
	}

}
