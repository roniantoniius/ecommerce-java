package com.roniantonius.ecommerce.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.roniantonius.ecommerce.model.Keranjang;

public interface KeranjangRepository extends JpaRepository<Keranjang, UUID>{
	
}
