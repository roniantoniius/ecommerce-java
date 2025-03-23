package com.roniantonius.ecommerce.model;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "produk")
@Builder
public class Produk {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	
	@Column(nullable = false)
	private String nama;

	@Column(nullable = false)
	private String merek;
	
	@Column(nullable = false)
	private BigDecimal harga;
	
	@Column(nullable = false)
	private int inventory;
	
	@Column(nullable = false, columnDefinition = "TEXT")
	private String deskripsi;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "kategori_id", nullable = false)
	private Kategori kategori;
	
	@OneToMany(mappedBy = "produk", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Gambar> gambars;
}
