package com.roniantonius.ecommerce.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.roniantonius.ecommerce.model.Produk;

public interface ProdukRepository extends JpaRepository<Produk, UUID>{

	List<Produk> findByKategoriNama(String kategori);

	List<Produk> findByMerek(String merek);

	List<Produk> findByKategoriNamaAndMerek(String kategori, String merek);

	List<Produk> findByNama(String nama);

	List<Produk> findByMerekAndNama(String merek, String nama);

	Long countByMerekAndNama(String merek, String nama);

}