package com.roniantonius.ecommerce.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.roniantonius.ecommerce.model.Kategori;

public interface KategoriRepository extends JpaRepository<Kategori, UUID> {

	Kategori findByNama(String nama);

	boolean existsByNamaIgnoreCase(String nama);

}
