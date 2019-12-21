package com.example.customer.invite.service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

public interface FileService {

	/**
	 * Method to fetch customer details from a file
	 * 
	 * @return List of customers as JSON string
	 * @throws IOException
	 * @throws URISyntaxException
	 */
	List<String> fetchCustomerDetails() throws IOException, URISyntaxException;

}
