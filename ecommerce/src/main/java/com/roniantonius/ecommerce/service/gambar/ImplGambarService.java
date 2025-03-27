package com.roniantonius.ecommerce.service.gambar;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.sql.rowset.serial.SerialBlob;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.roniantonius.ecommerce.dto.GambarDto;
import com.roniantonius.ecommerce.exceptions.ResourceNotFoundException;
import com.roniantonius.ecommerce.model.Gambar;
import com.roniantonius.ecommerce.model.Produk;
import com.roniantonius.ecommerce.repositories.GambarRepository;
import com.roniantonius.ecommerce.service.produk.ImplProdukService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ImplGambarService implements GambarService{
	private final GambarRepository gambarRepository;
	private final ImplProdukService implProdukService;
	@Override
	public Gambar getGambarById(UUID id) {
		// TODO Auto-generated method stub
		return gambarRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Gambar tidak ditemukan dengan id " + id));
	}

	@Transactional
	@Override
	public void deleteGambarById(UUID id) {
		// TODO Auto-generated method stub
		gambarRepository.findById(id).ifPresentOrElse(
				gambarRepository::delete,
				() -> {throw new ResourceNotFoundException("Gambar tidak ditemukan dengan id " + id);
			});
	}

	@Transactional
	// method untuk simpan beberapa gambar dalam satu produk
	@Override
	public List<GambarDto> saveGambars(List<MultipartFile> files, UUID produkId) {
		// TODO Auto-generated method stub
		Produk produk = implProdukService.getProdukById(produkId);
		List<GambarDto> daftarGambarDtos = new ArrayList<>();
		for (MultipartFile file : files) {
			try {
				Gambar gambar = new Gambar();
				gambar.setNamaFile(file.getOriginalFilename());
				gambar.setTipeFile(file.getContentType());
				gambar.setGambar(new SerialBlob(file.getBytes()));
				gambar.setProduk(produk);
				
				String linkDownloadString = "/api/v1/images/image/download/" + gambar.getId();
				gambar.setDownloadUrl(linkDownloadString);
				
				Gambar gambarSimpan = gambarRepository.save(gambar);
				gambarSimpan.setDownloadUrl("/api/v1/images/image/download/" + gambarSimpan.getId());
				gambarRepository.save(gambarSimpan);
				
				GambarDto gambarDto = new GambarDto(); // dto disini supaya lebih efisien dan hanya load id, nama, dan link gambar aja
				gambarDto.setId(gambarSimpan.getId());
				gambarDto.setNamaFile(gambarSimpan.getNamaFile());
				gambarDto.setDownloadUrl(gambarSimpan.getDownloadUrl());
				
				daftarGambarDtos.add(gambarDto);
			} catch (IOException | SQLException e) {
				// TODO: handle exception
				throw new RuntimeException(e.getMessage());
			}
		}
		return daftarGambarDtos;
	}

	@Override
	public void updateGambar(MultipartFile file, UUID gambarId) {
		// TODO Auto-generated method stub
		Gambar gambar = getGambarById(gambarId);
		try {
			gambar.setNamaFile(file.getOriginalFilename());
			gambar.setGambar(new SerialBlob(file.getBytes()));
			gambarRepository.save(gambar);
		} catch (IOException | SQLException e) {
			// TODO: handle exception
			throw new RuntimeException(e.getMessage());
		}
	}
}
