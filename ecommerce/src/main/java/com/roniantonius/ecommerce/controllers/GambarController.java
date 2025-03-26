package com.roniantonius.ecommerce.controllers;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
import org.springframework.web.multipart.MultipartFile;

import com.roniantonius.ecommerce.dto.GambarDto;
import com.roniantonius.ecommerce.exceptions.ResourceNotFoundException;
import com.roniantonius.ecommerce.model.Gambar;
import com.roniantonius.ecommerce.response.ApiResponse;
import com.roniantonius.ecommerce.service.gambar.GambarService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(path = "/api/v1/gambars")
@RequiredArgsConstructor
public class GambarController {
	private final GambarService gambarService;
	
	@PostMapping(path = "/upload-gambars",  consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<ApiResponse> saveGambars(@RequestParam("files") List<MultipartFile> files, @RequestParam("produkId") UUID produkId){
		try {
			List<GambarDto> gambarsDtos = gambarService.saveGambars(files, produkId);
			return ResponseEntity.ok(new ApiResponse("Kirim gambar berhasil!", gambarsDtos));
		} catch (Exception e) {
			// TODO: handle exception
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Kirim gambar gagal!", e.getMessage()));
		}
	}
	
	// endpoint untuk mengunduh file
	@GetMapping(path = "/unduh-gambar/{id}")
	public ResponseEntity<Resource> downloadGambar(@PathVariable UUID id) {
	    try {
	        Gambar gambar = gambarService.getGambarById(id);
	        ByteArrayResource byteArrayResource = new ByteArrayResource(gambar.getGambar().getBytes(1, (int) gambar.getGambar().length()));
	        
	        return ResponseEntity.ok()
	                .contentType(MediaType.parseMediaType(gambar.getTipeFile()))
	                .header(org.springframework.http.HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + gambar.getNamaFile() + "\"")
	                .body(byteArrayResource);
	    } catch (SQLException e) {
	        // Handle SQL exception
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                .body(null); // Return null or handle it appropriately
	    } catch (ResourceNotFoundException e) {
	        // Handle resource not found exception
	        return ResponseEntity.status(HttpStatus.NOT_FOUND)
	                .body(null); // Return null or handle it appropriately
	    } catch (Exception e) {
	        // Handle any other exceptions
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                .body(null); // Return null or handle it appropriately
	    }
	}
	
	// endpoint update gambar
	@PutMapping(path = "/{id}/update-gambar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<ApiResponse> updateGambar(@PathVariable("id") UUID id,
	                                                @RequestParam("file") MultipartFile file) {
	    try {
	        Gambar gambar = gambarService.getGambarById(id);
	        if (gambar != null) {
	            gambarService.updateGambar(file, id);
	            return ResponseEntity.ok(new ApiResponse("Gambar berhasil diperbarui!", gambar));
	        }
	    } catch (ResourceNotFoundException e) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND)
	                             .body(new ApiResponse(e.getMessage(), null));
	    }
	    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                         .body(new ApiResponse("Update gambar gagal!", HttpStatus.INTERNAL_SERVER_ERROR));
	}
	
	// hapus gambar berdasakran id
	@DeleteMapping(path = "/{id}/delete-gambar")
	public ResponseEntity<ApiResponse> deleteGambar(@PathVariable UUID id){
		try {
			Gambar gambar = gambarService.getGambarById(id);
			if (gambar != null) {
				gambarService.deleteGambarById(id);
				return ResponseEntity.ok(new ApiResponse("Gambar berhasil dihapus!", gambar));
			}
		} catch (ResourceNotFoundException e) {
			// TODO: handle exception
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Delete gambar gagal!", HttpStatus.INTERNAL_SERVER_ERROR));
	}
}