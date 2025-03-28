package com.roniantonius.ecommerce.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.roniantonius.ecommerce.enums.StatusPesanan;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "isipesanan")
@Builder
public class IsiPesanan {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	
	@Column(nullable = false)
	private int kuantitas;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "pesanan_id", nullable = false)
	private Pesanan pesanan;
	
	@ManyToOne
	@JoinColumn(name = "produk_id", nullable = false)
	private Produk produk;
	
	
	@Column(nullable = false)
	private BigDecimal harga;


	public IsiPesanan(Pesanan pesanan, Produk produk, int kuantitas, BigDecimal harga) {
		this.pesanan = pesanan;
		this.produk = produk;
		this.kuantitas = kuantitas;
		this.harga = harga;
	}
	
}