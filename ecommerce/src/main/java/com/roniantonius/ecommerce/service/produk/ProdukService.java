package com.roniantonius.ecommerce.service.produk;

import java.util.List;
import java.util.UUID;

import com.roniantonius.ecommerce.model.Produk;
import com.roniantonius.ecommerce.request.AddProdukRequest;
import com.roniantonius.ecommerce.request.ProdukUpdateRequest;

public interface ProdukService {
	Produk addProduk(AddProdukRequest request);
	Produk getProdukById(UUID id);
	void deleteProdukById(UUID id);
	Produk updateProdukById(ProdukUpdateRequest request, UUID id);
	List<Produk> getAllProduks();
	List<Produk> getProduksByKategori(String kategori);
	List<Produk> getProduksByMerek(String merek);
	List<Produk> getProduksByKategoriAndMerek(String kategori, String merek);
	List<Produk> getProduksByNama(String nama);
	List<Produk> getProduksByMerekAndNama(String merek, String nama);
	Long countProduksByMerekAndNama(String merek, String nama);
}
