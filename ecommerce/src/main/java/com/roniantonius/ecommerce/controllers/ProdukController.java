package com.roniantonius.ecommerce.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.roniantonius.ecommerce.dto.ProdukDto;
import com.roniantonius.ecommerce.exceptions.ResourceNotFoundException;
import com.roniantonius.ecommerce.model.Produk;
import com.roniantonius.ecommerce.request.AddProdukRequest;
import com.roniantonius.ecommerce.request.ProdukUpdateRequest;
import com.roniantonius.ecommerce.response.ApiResponse;
import com.roniantonius.ecommerce.service.produk.ProdukService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/produks")
public class ProdukController {
	private final ProdukService produkService;
	
	@GetMapping(path = "/daftar-produk")
	public ResponseEntity<ApiResponse> getAllProduk(){
		List<Produk> produks = produkService.getAllProduks();
		List<ProdukDto> daftarProdukDtos = produkService.getConvertedProduks(produks);
		return ResponseEntity.ok(new ApiResponse("Berhasil!", daftarProdukDtos));
	}
	
	@GetMapping(path = "/{id}/produk")
	public ResponseEntity<ApiResponse> getProdukById(@PathVariable UUID id){
		try {
			Produk produk = produkService.getProdukById(id);
			ProdukDto produkDto = produkService.convertToDto(produk);
			return ResponseEntity.ok(new ApiResponse("Berhasil mendapat produk id", produkDto));
		} catch (ResourceNotFoundException e) {
			// TODO: handle exception
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
		}
	}
	
	@PostMapping(path = "/tambah-produk")
	public ResponseEntity<ApiResponse> addProduk(@RequestBody AddProdukRequest addProdukRequest){
		try {
			Produk produk = produkService.addProduk(addProdukRequest);
			ProdukDto produkDto = produkService.convertToDto(produk);
			return ResponseEntity.ok(new ApiResponse("Berhasil menambah produk", produkDto));
		} catch (ResourceNotFoundException e) {
			// TODO: handle exception
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
		}
	}
	
	@PutMapping(path = "/{id}/update-produk")
	public ResponseEntity<ApiResponse> updateProduk(@RequestBody ProdukUpdateRequest produkUpdateRequest, @PathVariable UUID id){
		try {
			Produk produk = produkService.updateProdukById(produkUpdateRequest, id);
			ProdukDto produkDto = produkService.convertToDto(produk);
			return ResponseEntity.ok(new ApiResponse("Update produk berhasil!", produkDto));
		} catch (ResourceNotFoundException e) {
			// TODO: handle exception
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
		}
	}
	
	@DeleteMapping(path = "/{id}/delete-produk")
	public ResponseEntity<ApiResponse> deleteProduk(@PathVariable UUID id){
		try {
			produkService.deleteProdukById(id);
			return ResponseEntity.ok(new ApiResponse("Hapus produk berhasil!", id));
		} catch (ResourceNotFoundException e) {
			// TODO: handle exception
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
		}
	}
	
	@GetMapping(path = "/filter-produk-merek-nama/merek-nama")
	public ResponseEntity<ApiResponse> getProdukByMerekDanNama(@RequestParam String merek, @RequestParam String nama){
		try {
			List<Produk> daftarProduks = produkService.getProduksByMerekAndNama(merek, nama);
			
			if (daftarProduks.isEmpty()) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Produk dengan Merek dan Nama tersebut tidak ditemukan", null));
			}
			List<ProdukDto> daftarProdukDtos = produkService.getConvertedProduks(daftarProduks);
			return ResponseEntity.ok(new ApiResponse("berhasil", daftarProdukDtos));
		} catch (Exception e) {
			// TODO: handle exception
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
		}
	}
	
	@GetMapping(path = "/filter-produk-kategori-merek/kategori-merek")
	public ResponseEntity<ApiResponse> getProdukByKategoriDanMerek(@RequestParam String kategori, @RequestParam String merek){
		try {
			List<Produk> daftarProduks = produkService.getProduksByKategoriAndMerek(kategori, merek);
			if (daftarProduks.isEmpty()) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Produk dengan Kategori atau Merek tersebut tidak ditemukan", null));
			}
			List<ProdukDto> daftarProdukDtos = produkService.getConvertedProduks(daftarProduks);
			return ResponseEntity.ok(new ApiResponse("berhasil", daftarProdukDtos));
		} catch (Exception e) {
			// TODO: handle exception
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
		}
	}
	
	@GetMapping(path = "/filter-produk-nama/{nama}/semua")
	public ResponseEntity<ApiResponse> getProdukByNama(@PathVariable String nama){
		try {
			List<Produk> daftarProduks = produkService.getProduksByNama(nama);
			if (daftarProduks.isEmpty()) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Daftar Produk dengan nama tersebut tidak ditemukan", null));
			}
			List<ProdukDto> daftarProdukDtos = produkService.getConvertedProduks(daftarProduks);
			return ResponseEntity.ok(new ApiResponse("berhasil", daftarProdukDtos));
		} catch (Exception e) {
			// TODO: handle exception
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
		}
	}
	
	@GetMapping(path = "/filter-produk-merek/merek")
	public ResponseEntity<ApiResponse> getProdukByMerek(@RequestParam String merek){
		try {
			List<Produk> daftarProduks = produkService.getProduksByMerek(merek);
			if (daftarProduks.isEmpty()) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Daftar Produk dengan merek tersebut tidak ditemukan", null));
			}
			List<ProdukDto> daftarProdukDtos = produkService.getConvertedProduks(daftarProduks);
			return ResponseEntity.ok(new ApiResponse("berhasil", daftarProdukDtos));
		} catch (Exception e) {
			// TODO: handle exception
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
		}
	}
	
	@GetMapping(path = "/filter-produk-kategori/{kategori}/semua")
	public ResponseEntity<ApiResponse> getProdukByKategori(@PathVariable String kategori){
		try {
			List<Produk> daftarProduks = produkService.getProduksByKategori(kategori);
			if (daftarProduks.isEmpty()) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Daftar Produk dengan kategori tersebut tidak ditemukan", null));
			}
			List<ProdukDto> daftarProdukDtos = produkService.getConvertedProduks(daftarProduks);
			return ResponseEntity.ok(new ApiResponse("berhasil", daftarProdukDtos));
		} catch (Exception e) {
			// TODO: handle exception
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
		}
	}
	
	@GetMapping(path = "/count/merek-nama")
	public ResponseEntity<ApiResponse> countProduksByMerekAndNama(@RequestParam String merek, @RequestParam String nama){
		try {
			var hitungJumlahProduk = produkService.countProduksByMerekAndNama(merek, nama);
			return ResponseEntity.ok(new ApiResponse("Berhasil menghitung jumlah produk berdasarkan merek dan nama produk", hitungJumlahProduk));
		} catch (Exception e) {
			// TODO: handle exception
			return ResponseEntity.ok(new ApiResponse(e.getMessage(), null));
		}
	}
}
