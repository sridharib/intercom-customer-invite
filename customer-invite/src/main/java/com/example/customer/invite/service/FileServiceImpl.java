package com.example.customer.invite.service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class FileServiceImpl implements FileService {

	@Value("${customer.file-name}")
	private String customerFile;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<String> fetchCustomerDetails() throws IOException, URISyntaxException {
		return Files.readAllLines(Paths.get(ClassLoader.getSystemResource(customerFile).toURI()));
	}
}
