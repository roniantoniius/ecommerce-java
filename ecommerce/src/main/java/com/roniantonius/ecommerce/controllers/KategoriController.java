package com.roniantonius.ecommerce.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.roniantonius.ecommerce.model.Kategori;
import com.roniantonius.ecommerce.response.ApiResponse;
import com.roniantonius.ecommerce.service.kategori.KategoriService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/kategoris")
public class KategoriController {
	private final KategoriService kategoriService;
	
	@GetMapping(path = "/daftar-kategori")
	public ResponseEntity<ApiResponse> getAllKategoris(){
		try {
			List<Kategori> daftarKategoris = kategoriService.getAllKategori();
			return ResponseEntity.ok(new ApiResponse("Kategori ditemukan!", daftarKategoris));
		} catch (Exception e) {
			// TODO: handle exception
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Error: ", HttpStatus.INTERNAL_SERVER_ERROR));
		}
	}
	
	@PostMapping(path = "/tambah-kategori")
	public ResponseEntity<ApiResponse> addKategoris(@RequestBody Kategori nama){
		try {
			Kategori kategori = kategoriService.addKategori(nama);
			return ResponseEntity.ok(new ApiResponse("Berhasil!", kategori));
		} catch (Exception e) {
			// TODO: handle exception
			return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse(e.getMessage(), null));
		}
	}
	
	@GetMapping(path = "/{id}/kategoris")
	public ResponseEntity<ApiResponse> getKategoriById(@PathVariable UUID id){
		try {
			Kategori kategoriById = kategoriService.getKategoriById(id);
			return ResponseEntity.ok(new ApiResponse("Berhasill!", kategoriById));
		} catch (Exception e) {
			// TODO: handle exception
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
		}
	}
	
	@GetMapping(path = "/{nama}/kategoris")
	public ResponseEntity<ApiResponse> getKategoriByNama(@PathVariable String nama){
		try {
			Kategori kategoriByNama = kategoriService.getKategoriByNama(nama);
			return ResponseEntity.ok(new ApiResponse("Berhasill!", kategoriByNama));
		} catch (Exception e) {
			// TODO: handle exception
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
		}
	}
	
	@DeleteMapping(path = "/{id}/hapus-kategori")
	public ResponseEntity<ApiResponse> deleteKategori(@PathVariable UUID id){
		try {
			kategoriService.deleteKategoriById(id);
			return ResponseEntity.ok(new ApiResponse("Berhasil dihapus!", null));
		} catch (Exception e) {
			// TODO: handle exception
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
		}
	}
	
	@PutMapping(path = "/{id}/update-kategori")
	public ResponseEntity<ApiResponse> updateKategori(@PathVariable UUID id, @RequestBody Kategori kategori){
		try {
			Kategori kategoriUpdate = kategoriService.updateKategori(kategori, id);
			return ResponseEntity.ok(new ApiResponse("Kategori berhasil diupdate " + kategoriUpdate.getNama(), kategoriUpdate));
		} catch (Exception e) {
			// TODO: handle exception
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
		}
	}

}
