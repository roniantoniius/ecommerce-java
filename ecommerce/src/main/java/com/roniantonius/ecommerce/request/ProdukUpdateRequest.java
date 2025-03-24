package com.roniantonius.ecommerce.request;

import java.math.BigDecimal;
import java.util.UUID;

import com.roniantonius.ecommerce.model.Kategori;

import lombok.Data;

@Data
public class ProdukUpdateRequest {
	private UUID id;
	
	private String nama;

	private String merek;
	
	private BigDecimal harga;
	
	private int inventory;
	
	private String deskripsi;
	
	private Kategori kategori;
}
