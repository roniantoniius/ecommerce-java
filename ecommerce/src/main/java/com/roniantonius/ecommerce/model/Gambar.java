package com.roniantonius.ecommerce.model;

import java.sql.Blob;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "gambar")
@Builder
public class Gambar {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	
	@Column(nullable = false)
	private String namaFile;
	
	@Column(nullable = false)
	private String tipeFile;
	
	@Lob
	private Blob gambar;
	
	@Column(nullable = false)
	private String downloadUrl;
	
	@ManyToOne
	@JoinColumn(name = "produk_id")
	private Produk produk;
}
