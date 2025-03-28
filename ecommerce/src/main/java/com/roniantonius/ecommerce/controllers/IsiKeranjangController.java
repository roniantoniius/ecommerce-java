package com.roniantonius.ecommerce.controllers;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.roniantonius.ecommerce.exceptions.ResourceNotFoundException;
import com.roniantonius.ecommerce.response.ApiResponse;
import com.roniantonius.ecommerce.service.keranjang.ImplKeranjangService;
import com.roniantonius.ecommerce.service.keranjang.IsiKeranjangService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/isi-keranjangs")
public class IsiKeranjangController {
	private final IsiKeranjangService isiKeranjangService;
	private final ImplKeranjangService implKeranjangService;
	
	@PostMapping(path = "/tambah")
	public ResponseEntity<ApiResponse> addIsiKeranjang(UUID keranjangId, UUID produkId, int kuantitas){
		try {
			if (keranjangId == null) {
				keranjangId = implKeranjangService.initializeNewKeranjang();
			}
			
			isiKeranjangService.addIsiToKeranjang(keranjangId, produkId, kuantitas);
			return ResponseEntity.ok(new ApiResponse("Produk berhasil ditambahkan pada keranjang kamu!", null));
		} catch (ResourceNotFoundException e) {
			// TODO: handle exception
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
		}
	}
	
	@DeleteMapping(path = "/delete-keranjang-produk/{keranjangId}/{produkId}")
	public ResponseEntity<ApiResponse> removeIsiFromKeranjang(@PathVariable UUID keranjangId, @PathVariable UUID produkId){
		try {
			isiKeranjangService.removeIsiFromKeranjang(keranjangId, produkId);
			return ResponseEntity.ok(new ApiResponse("Produk berhasil dihapus dari keranjang!", null));
		} catch (ResourceNotFoundException e) {
			// TODO: handle exception
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
		}
	}
	
	@PutMapping(path = "/update-keranjang-produk/{keranjangId}/{produkId}")
	public ResponseEntity<ApiResponse> updateIsiKuantitas(@PathVariable UUID keranjangId, @PathVariable UUID produkId, @RequestParam Integer kuantitas){
		try {
			isiKeranjangService.updateIsiKuantitas(keranjangId, produkId, kuantitas);
			return ResponseEntity.ok(new ApiResponse("Kuantitas produk berhasil diperbarui", null));
		} catch (ResourceNotFoundException e) {
			// TODO: handle exception
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
		}
	}
}