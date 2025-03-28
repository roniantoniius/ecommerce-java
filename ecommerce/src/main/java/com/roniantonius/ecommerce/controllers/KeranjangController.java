package com.roniantonius.ecommerce.controllers;

import java.math.BigDecimal;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.roniantonius.ecommerce.exceptions.ResourceNotFoundException;
import com.roniantonius.ecommerce.model.Keranjang;
import com.roniantonius.ecommerce.response.ApiResponse;
import com.roniantonius.ecommerce.service.keranjang.KeranjangService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/keranjangs")
public class KeranjangController {
	private final KeranjangService keranjangService;
	
	@GetMapping("/{id}")
	public ResponseEntity<ApiResponse> getKeranjang(@PathVariable UUID id){
		try {
			Keranjang keranjang = keranjangService.getKeranjang(id);
			return ResponseEntity.ok(new ApiResponse("Berhasil mendapat keranjang", keranjang));
		} catch (ResourceNotFoundException e) {
			// TODO: handle exception
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
		}
	}
	
	@DeleteMapping("/hapus/{id}")
	public ResponseEntity<ApiResponse> clearKeranjang(@PathVariable UUID id){
		try {
			keranjangService.clearKeranjang(id);
			return ResponseEntity.ok(new ApiResponse("Keranjang berhasil dibersihkan", null));
		} catch (ResourceNotFoundException e) {
			// TODO: handle exception
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
		}
	}
	
	@GetMapping("/{id}/harga-total")
	public ResponseEntity<ApiResponse> getHargaTotal(@PathVariable UUID id){
		try {
			BigDecimal harTot = keranjangService.getHargaTotal(id);
			return ResponseEntity.ok(new ApiResponse("Harga keranjang berhasil dihitung", harTot));
		} catch (ResourceNotFoundException e) {
			// TODO: handle exception
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
		}
	}
	
}
