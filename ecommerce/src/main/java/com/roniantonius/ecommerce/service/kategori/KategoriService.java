package com.roniantonius.ecommerce.service.kategori;

import java.util.List;
import java.util.UUID;

import com.roniantonius.ecommerce.model.Kategori;

public interface KategoriService {
	Kategori getKategoriById(UUID id);
	Kategori getKategoriByNama(String nama);
	List<Kategori> getAllKategori();
	Kategori addKategori(Kategori kategori);
	Kategori updateKategori(Kategori kategori, UUID id);
	void deleteKategoriById(UUID id);
}
