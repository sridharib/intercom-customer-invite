package com.example.customer.invite.service;

import java.util.List;

import com.example.customer.model.Customer;

public interface CustomerInviteService {

	/**
	 * Method to fetch by range and unit
	 * 
	 * @param range
	 * @param unit
	 * @return List of customers who falls in the range
	 * @throws Exception 
	 */
	List<Customer> fetchCustomers(Double range, String unit) throws Exception;

}
