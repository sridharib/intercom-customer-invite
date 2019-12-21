package com.example.customer.invite.service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.customer.invite.utils.CustomerInviteUtils;
import com.example.customer.model.Customer;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CustomerInviteServiceImpl implements CustomerInviteService {

	@Value("${company.latitude}")
	private Double companyLatitude;

	@Value("${company.longitude}")
	private Double companyLongitude;

	@Autowired
	private FileService fileService;

	private ObjectMapper objectMapper = new ObjectMapper();

	/**
	 * {@inheritDoc}
	 * 
	 * @throws Exception
	 */
	@Override
	public List<Customer> fetchCustomers(Double range, String unit) throws Exception {

		log.info("Fetching customers in the range {} {}", range, unit);

		if (range < 0D) {
			throw new Exception("Range can not be less than zero");
		}

		if (!unit.equalsIgnoreCase("K")) {
			throw new UnsupportedOperationException("Unit not supported");
		}

		List<Customer> customers = new ArrayList<>();
		try {
			List<String> customerDetails = fileService.fetchCustomerDetails();

			for (String customerDetail : customerDetails) {
				Customer customer = objectMapper.readValue(customerDetail, Customer.class);

				Double distance = CustomerInviteUtils.getDistance(customer.getLatitude(), customer.getLongitude(), companyLatitude,
						companyLongitude, unit);
				if (distance <= range) {
					customers.add(customer);
				}
			}
		} catch (IOException | URISyntaxException e) {
			log.error(e.getMessage());
		}

		// Sort by user id
		customers.sort(Comparator.comparingLong(Customer::getUserId));
		return customers;
	}

}
