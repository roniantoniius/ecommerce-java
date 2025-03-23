package com.roniantonius.ecommerce.exceptions;

public class ProdukNotFoundException extends RuntimeException{
	public ProdukNotFoundException(String pesan) {
		super(pesan);
	}
}
