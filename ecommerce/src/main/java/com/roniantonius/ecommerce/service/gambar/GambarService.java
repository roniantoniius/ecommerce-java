package com.roniantonius.ecommerce.service.gambar;

import java.util.List;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import com.roniantonius.ecommerce.dto.GambarDto;
import com.roniantonius.ecommerce.model.Gambar;

public interface GambarService {
	Gambar getGambarById(UUID id);
	void deleteGambarById(UUID id);
	List<GambarDto> saveGambars(List<MultipartFile> files, UUID produkId);
	void updateGambar(MultipartFile file, UUID gambarId);
}
