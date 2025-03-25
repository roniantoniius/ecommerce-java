package com.roniantonius.ecommerce.response;

import lombok.AllArgsConstructor;
import lombok.Data;

// class for return data to frontend as a request
@AllArgsConstructor
@Data
public class ApiResponse {
	private String message;
	private Object data;
}
