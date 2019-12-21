package com.example.customer.invite;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.example.customer.invite.service.CustomerInviteService;
import com.example.customer.model.Customer;

import lombok.extern.slf4j.Slf4j;

@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages = "com.example.customer.invite")
@Slf4j
@SpringBootApplication
public class CustomerInviteApplication implements CommandLineRunner {

	@Autowired
	private CustomerInviteService customerInviteService;

	@Value("${customer.file-name}")
	private String customerFile;

	public static void main(String[] args) {
		SpringApplication.run(CustomerInviteApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		List<Customer> customers = customerInviteService.fetchCustomers(100D, "K");
		log.info("These customers are within the given range {} {}", 100D, "K");
		log.info("{}", customers);
	}

}
