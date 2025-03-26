package com.roniantonius.ecommerce.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TokoConfig {
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
}
