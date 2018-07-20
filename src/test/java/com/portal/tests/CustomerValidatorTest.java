package com.portal.tests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

import com.portal.dto.CustomerDto;
import com.portal.util.CustomerValidator;

@RunWith(SpringRunner.class)
public class CustomerValidatorTest {

	@Mock
	CustomerValidator customerValidator;

	@Test
	public void validateCustomerRequestTestForSuccess() {
		CustomerDto CustomerDtoAddRequest = new CustomerDto();
		CustomerDtoAddRequest.setCustomerId(1);
		CustomerDtoAddRequest.setFirstName("firstNameTest");
		CustomerDtoAddRequest.setLastName("LastNameTest");
		CustomerDtoAddRequest.setEmail("email@test.com");
		CustomerDtoAddRequest.setMobileNumber(Long.valueOf("1234567890"));

		Mockito.when(customerValidator.validateCustomerRequest(CustomerDtoAddRequest)).thenReturn(true);

		boolean actual = customerValidator.validateCustomerRequest(CustomerDtoAddRequest);
		assertEquals(actual, true);

	}

	@Test
	public void validateCustomerRequestTestForFail() {
		CustomerDto CustomerDtoAddRequest = new CustomerDto();
		CustomerDtoAddRequest.setCustomerId(1);
		CustomerDtoAddRequest.setFirstName("firstNameTest123");
		CustomerDtoAddRequest.setLastName("LastNameTest");
		CustomerDtoAddRequest.setEmail("email@test.com");
		CustomerDtoAddRequest.setMobileNumber(Long.valueOf("1234567890"));

		Mockito.when(customerValidator.validateCustomerRequest(CustomerDtoAddRequest)).thenReturn(false);

		boolean actual = customerValidator.validateCustomerRequest(CustomerDtoAddRequest);
		assertEquals(actual, false);

	}

	@Test
	public void validateMobileNumberTestForSuccess() {

		Long mobileNumber = new Long(1234567890);

		Mockito.when(customerValidator.validateMobileNumber(mobileNumber)).thenReturn(true);
		boolean actual = customerValidator.validateMobileNumber(Long.valueOf("1234567890"));
		assertEquals(actual, true);

	}
}
