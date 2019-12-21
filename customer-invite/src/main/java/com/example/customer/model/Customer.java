package com.example.customer.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class Customer {

	@JsonProperty("user_id")
	private Long userId;
	
	@JsonProperty("name")
	private String name;
	
	@JsonProperty("latitude")
	private Double latitude;
	
	@JsonProperty("longitude")
	private Double longitude;
}
