package com.roniantonius.ecommerce.service.produk;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.roniantonius.ecommerce.dto.GambarDto;
import com.roniantonius.ecommerce.dto.ProdukDto;
import com.roniantonius.ecommerce.exceptions.ProdukNotFoundException;
import com.roniantonius.ecommerce.model.Gambar;
import com.roniantonius.ecommerce.model.Kategori;
import com.roniantonius.ecommerce.model.Produk;
import com.roniantonius.ecommerce.repositories.GambarRepository;
import com.roniantonius.ecommerce.repositories.KategoriRepository;
import com.roniantonius.ecommerce.repositories.ProdukRepository;
import com.roniantonius.ecommerce.request.AddProdukRequest;
import com.roniantonius.ecommerce.request.ProdukUpdateRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ImplProdukService implements ProdukService {

	private final ProdukRepository produkRepository;
	private final KategoriRepository kategoriRepository;
	private final GambarRepository gambarRepository;
	private final ModelMapper modelMapper;
	
	@Override
	public Produk addProduk(AddProdukRequest request) {
		// TODO Auto-generated method stub
		Kategori kategori = Optional.ofNullable(kategoriRepository.findByNama(request.getKategori().getNama()))
				.orElseGet(() -> {
					Kategori kategoriBaru = new Kategori(request.getKategori().getNama());
					return kategoriRepository.save(kategoriBaru);
				});
		request.setKategori(kategori);
		return produkRepository.save(createProduk(request, kategori));
	}
	
	private Produk createProduk(AddProdukRequest request, Kategori kategori) {
		return new Produk(
				request.getNama(),
				request.getMerek(),
				request.getHarga(),
				request.getInventory(),
				request.getDeskripsi(),
				kategori
		);
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
	public Produk updateProdukById(ProdukUpdateRequest request, UUID id) {
		// TODO Auto-generated method stub
		return produkRepository.findById(id)
				.map(updateProduk -> updateExistingProduk(updateProduk, request))
				.map(produkRepository::save)
				.orElseThrow(() -> new ProdukNotFoundException("Produk yang akan diperbarui tidak ditemukan!"));
	}
	
	private Produk updateExistingProduk(Produk produk, ProdukUpdateRequest request) {
		produk.setNama(request.getNama());
		produk.setMerek(request.getMerek());
		produk.setHarga(request.getHarga());
		produk.setInventory(request.getInventory());
		produk.setDeskripsi(request.getDeskripsi());
		Kategori kategori = kategoriRepository.findByNama(request.getKategori().getNama());
		produk.setKategori(kategori);
		return produk;
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
	
	@Override
	public List<ProdukDto> getConvertedProduks(List<Produk> produks){
		return produks.stream().map(this::convertToDto).toList();
	}
	
	@Override
	public ProdukDto convertToDto(Produk produk) {
		ProdukDto produkDto = modelMapper.map(produk, ProdukDto.class);
		List<Gambar> daftarGambars = gambarRepository.findByProdukId(produk.getId());
		List<GambarDto> daftarGambarDtos = daftarGambars.stream()
				.map(gambar -> modelMapper.map(gambar, GambarDto.class))
				.toList();
		produkDto.setGambars(daftarGambarDtos);
		return produkDto;
	}

}
