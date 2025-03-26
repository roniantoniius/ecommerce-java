package com.roniantonius.ecommerce.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.roniantonius.ecommerce.model.Gambar;

public interface GambarRepository extends JpaRepository<Gambar, UUID>{
	List<Gambar> findByProdukId(UUID produkId);
}
