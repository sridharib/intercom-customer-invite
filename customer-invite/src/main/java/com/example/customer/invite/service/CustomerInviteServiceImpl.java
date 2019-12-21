package com.example.customer.invite.service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

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
	 */
	@Override
	public List<Customer> fetchCustomers(Double range, String unit) {

		log.info("Fetching customers in the range {} {}", range, unit);

		List<Customer> customers = new ArrayList<>();
		try {
			List<String> customerDetails = fileService.fetchCustomerDetails();

			for (String customerDetail : customerDetails) {
				Customer customer = objectMapper.readValue(customerDetail, Customer.class);

				if (getDistance(customer.getLatitude(), customer.getLongitude(), companyLatitude, companyLongitude,
						unit) <= 100D) {
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

	/**
	 * Calculate distance between two GPS coordinates
	 * 
	 * @param latitude1
	 * @param longitude1
	 * @param latitude2
	 * @param longitude2
	 * @param unit
	 * @return The distance between two GPS coordinates
	 */
	private Double getDistance(double latitude1, double longitude1, double latitude2, double longitude2, String unit) {
		if ((latitude1 == latitude2) && (longitude1 == longitude2)) {
			return 0D;
		} else {
			double theta = longitude1 - longitude2;
			double dist = Math.sin(Math.toRadians(latitude1)) * Math.sin(Math.toRadians(latitude2))
					+ Math.cos(Math.toRadians(latitude1)) * Math.cos(Math.toRadians(latitude2))
							* Math.cos(Math.toRadians(theta));
			dist = Math.acos(dist);
			dist = Math.toDegrees(dist);
			dist = dist * 60 * 1.1515;
			if (unit.equals("K")) {
				dist = dist * 1.609344;
			} else if (unit.equals("N")) {
				dist = dist * 0.8684;
			}
			return (dist);
		}
	}

}
