package com.roniantonius.ecommerce.dto;

import java.util.UUID;

import lombok.Data;

@Data
public class GambarDto {

	private UUID id;
	
	private String namaFile;
	
	private String downloadUrl;
}