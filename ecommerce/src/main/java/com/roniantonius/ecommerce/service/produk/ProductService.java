package com.roniantonius.ecommerce.service.produk;

import java.util.List;
import java.util.UUID;

import com.roniantonius.ecommerce.model.Produk;

public interface ProductService {
	Produk addProduk(Produk produk);
	Produk getProdukById(UUID id);
	void deleteProdukById(UUID id);
	void updateProdukById(Produk produk, UUID id);
	List<Produk> getAllProduks();
	List<Produk> getProduksByKategori(String kategori);
	List<Produk> getProduksByMerek(String merek);
	List<Produk> getProduksByKategoriAndMerek(String kategori, String merek);
	List<Produk> getProduksByNama(String nama);
	List<Produk> getProduksByMerekAndNama(String merek, String nama);
	Long countProduksByMerekAndNama(String merek, String nama);
}
