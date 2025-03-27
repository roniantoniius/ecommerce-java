package com.roniantonius.ecommerce.service.kategori;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.roniantonius.ecommerce.exceptions.ResourceNotFoundException;
import com.roniantonius.ecommerce.model.Kategori;
import com.roniantonius.ecommerce.repositories.KategoriRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ImplKategoriService implements KategoriService {
	private final KategoriRepository kategoriRepository;
	
	@Override
	public Kategori getKategoriById(UUID id) {
		// TODO Auto-generated method stub
		return kategoriRepository.findById(id).orElseThrow(
				() -> new ResourceNotFoundException("Kategori tidak ditemukan")
		);
	}

	@Override
	public Kategori getKategoriByNama(String nama) {
		// TODO Auto-generated method stub
		return kategoriRepository.findByNama(nama);
	}

	@Override
	public List<Kategori> getAllKategori() {
		// TODO Auto-generated method stub
		return kategoriRepository.findAll();
	}

	@Transactional
	@Override
	public Kategori addKategori(Kategori kategori) {
		// TODO Auto-generated method stub
		if (kategoriRepository.existsByNamaIgnoreCase(kategori.getNama())) {
			throw new IllegalArgumentException("Kategori tersebut sudah ada dengan " + kategori.getNama());
		}
		return kategoriRepository.save(kategori);
	}

	@Override
	public Kategori updateKategori(Kategori kategori, UUID id) {
		// TODO Auto-generated method stub
		return Optional.ofNullable(getKategoriById(id)).map(kategoriLama -> {
			kategoriLama.setNama(kategori.getNama());
			return kategoriRepository.save(kategoriLama);
		}).orElseThrow(() -> new ResourceNotFoundException("Kategori yang ingin diperbarui tidak ditemukan"));
	}

	@Transactional
	@Override
	public void deleteKategoriById(UUID id) {
		// TODO Auto-generated method stub
		// tambahin logika supaya hanya bisa dihapus ketika jumlah Produk 0 pada kategori tersebut
		kategoriRepository.findById(id).ifPresentOrElse(
				kategoriRepository::delete,
				() -> { throw new ResourceNotFoundException("Produk yang ingin dihapus tidak ditemukan");
		});
	}

}
