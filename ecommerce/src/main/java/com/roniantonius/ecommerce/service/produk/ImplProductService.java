package com.roniantonius.ecommerce.service.produk;

import java.util.List;
import java.util.UUID;

import com.roniantonius.ecommerce.exceptions.ProdukNotFoundException;
import com.roniantonius.ecommerce.model.Produk;
import com.roniantonius.ecommerce.repositories.ProdukRepository;

public class ImplProductService implements ProductService {

	private ProdukRepository produkRepository;
	@Override
	public Produk addProduk(Produk produk) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Produk getProdukById(UUID id) {
		// TODO Auto-generated method stub
		return produkRepository.findById(id).orElseThrow(() -> new ProdukNotFoundException("Produk atau barang tidak ditemukan!"));
	}

	@Override
	public void deleteProdukById(UUID id) {
		// TODO Auto-generated method stub
		produkRepository.findById(id).ifPresentOrElse(
				produkRepository::delete,
				() -> {throw new ProdukNotFoundException("Produk yang ingin dihapus tidak ditemukan");});
	}

	@Override
	public void updateProdukById(Produk produk, UUID id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Produk> getAllProduks() {
		// TODO Auto-generated method stub
		return produkRepository.findAll();
	}

	@Override
	public List<Produk> getProduksByKategori(String kategori) {
		// TODO Auto-generated method stub
		return produkRepository.findByKategoriNama(kategori);
	}

	@Override
	public List<Produk> getProduksByMerek(String merek) {
		// TODO Auto-generated method stub
		return produkRepository.findByMerek(merek);
	}

	@Override
	public List<Produk> getProduksByKategoriAndMerek(String kategori, String merek) {
		// TODO Auto-generated method stub
		return produkRepository.findByKategoriNamaAndMerek(kategori, merek);
	}

	@Override
	public List<Produk> getProduksByNama(String nama) {
		// TODO Auto-generated method stub
		return produkRepository.findByNama(nama);
	}

	@Override
	public List<Produk> getProduksByMerekAndNama(String merek, String nama) {
		// TODO Auto-generated method stub
		return produkRepository.findByMerekAndNama(merek, nama);
	}

	@Override
	public Long countProduksByMerekAndNama(String merek, String nama) {
		// TODO Auto-generated method stub
		return produkRepository.countByMerekAndNama(merek, nama);
	}

}
