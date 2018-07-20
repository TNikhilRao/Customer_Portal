package com.portal.tests;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.portal.dto.CustomerDto;
import com.portal.util.CustomerValidator;

@RunWith(PowerMockRunner.class)
@PrepareForTest(CustomerValidator.class)
@Ignore
public class CustomerValidatorTests {

	/*
	 * @Mock private CustomerDto CustomerDtoAddRequest;
	 */

	@Test
	public void validateCustomerRequestTestForSuccess() {
		CustomerDto CustomerDtoAddRequest = new CustomerDto();
		CustomerDtoAddRequest.setFirstName("firstNameTest");
		CustomerDtoAddRequest.setLastName("LastNameTest");
		CustomerDtoAddRequest.setEmail("email@test.com");
		CustomerDtoAddRequest.setMobileNumber(Long.valueOf("1234567890"));

		PowerMockito.mockStatic(CustomerValidator.class);
		// PowerMockito.when(CustomerValidator.validateCustomerRequest(CustomerDtoAddRequest)).thenReturn(true);
		// boolean actual =
		// CustomerValidator.validateCustomerRequest(CustomerDtoAddRequest);
		// assertEquals(actual, true);

	}

	@Test
	public void validateCustomerRequestTestForFail() {
		CustomerDto CustomerDtoAddRequest = new CustomerDto();
		CustomerDtoAddRequest.setCustomerId(1);
		CustomerDtoAddRequest.setFirstName("firstNameTest789");
		CustomerDtoAddRequest.setLastName("Last@Name12Test");
		CustomerDtoAddRequest.setEmail("email@test.com");
		CustomerDtoAddRequest.setMobileNumber(Long.valueOf("1234567890"));

		PowerMockito.mockStatic(CustomerValidator.class);
		// PowerMockito.when(CustomerValidator.validateCustomerRequest(CustomerDtoAddRequest)).thenReturn(false);
		// boolean actual =
		// CustomerValidator.validateCustomerRequest(CustomerDtoAddRequest);
		// assertEquals(actual, false);

	}

	@Test
	public void validateCustomerUpdateRequestTestForSuccess() {
		CustomerDto CustomerDtoAddRequest = new CustomerDto();
		CustomerDtoAddRequest.setCustomerId(1);
		CustomerDtoAddRequest.setFirstName("firstNameTest");
		CustomerDtoAddRequest.setLastName("Last@NameTest");
		CustomerDtoAddRequest.setEmail("email@test.com");
		CustomerDtoAddRequest.setMobileNumber(Long.valueOf("1234567890"));

		PowerMockito.mockStatic(CustomerValidator.class);
		// PowerMockito.when(CustomerValidator.validateCustomerRequest(CustomerDtoAddRequest)).thenReturn(true);
		// boolean actual =
		// CustomerValidator.validateCustomerRequest(CustomerDtoAddRequest);
		// assertEquals(actual, true);

	}

	@Test
	public void validateMobileNumberTestForSuccess() {

		// CustomerDtoAddRequest.setMobileNumber(Long.valueOf("1234567890"));

		PowerMockito.mockStatic(CustomerValidator.class);
		// PowerMockito.when(CustomerValidator.validateMobileNumber(Long.valueOf("1234567890")).thenReturn(true);
		// boolean actual =
		// customerValidator.validateMobileNumber(Long.valueOf("1234567890"));
		// assertEquals(actual, false);

	}

}
