package com.example.customer.invite;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;

import com.example.customer.invite.service.CustomerInviteService;
import com.example.customer.invite.service.CustomerInviteServiceImpl;
import com.example.customer.invite.service.FileService;
import com.example.customer.model.Customer;

@SpringBootTest
class CustomerInviteServiceTests {

	@Mock
	private FileService fileService;

	@InjectMocks
	private CustomerInviteService service = new CustomerInviteServiceImpl();

	@BeforeEach
	void beforeEach() {

		MockitoAnnotations.initMocks(this);
		ReflectionTestUtils.setField(service, "companyLatitude", 53.339428);
		ReflectionTestUtils.setField(service, "companyLongitude", -6.257664);
	}

	@Test
	void whenFetchCutomersCalledThenExpectCutomers() throws Exception {

		List<String> customerDetails = new ArrayList<>();
		customerDetails.add(
				"{\"latitude\": \"54.180238\", \"user_id\": 17, \"name\": \"Patricia Cahill\", \"longitude\": \"-5.920898\"}");
		customerDetails.add(
				"{\"latitude\": \"53.1489345\", \"user_id\": 31, \"name\": \"Alan Behan\", \"longitude\": \"-6.8422408\"}");
		customerDetails.add(
				"{\"latitude\": \"51.92893\", \"user_id\": 1, \"name\": \"Alice Cahill\", \"longitude\": \"-10.27699\"}");

		when(fileService.fetchCustomerDetails()).thenReturn(customerDetails);

		List<Customer> fetchCustomers = service.fetchCustomers(100D, "K");

		assertNotNull(fetchCustomers);
		assertEquals(2, fetchCustomers.size());
	}

	@Test
	void whenFetchCutomersCalledThenExpectNoCutomers() throws Exception {

		List<String> customerDetails = new ArrayList<>();
		customerDetails.add(
				"{\"latitude\": \"51.92893\", \"user_id\": 1, \"name\": \"Alice Cahill\", \"longitude\": \"-10.27699\"}");

		when(fileService.fetchCustomerDetails()).thenReturn(customerDetails);

		List<Customer> fetchCustomers = service.fetchCustomers(100D, "K");

		assertNotNull(fetchCustomers);
		assertEquals(0, fetchCustomers.size());
	}

	@Test
	void whenFetchCutomersCalledWithInvalidDistanceThenExpectException() throws Exception {

		assertThrows(Exception.class, () -> {
			service.fetchCustomers(-100D, "K");
		});
	}
	
	@Test
	void whenFetchCutomersCalledWithInvalidUnitThenExpectException() throws Exception {

		assertThrows(UnsupportedOperationException.class, () -> {
			service.fetchCustomers(100D, "J");
		});
	}

}
