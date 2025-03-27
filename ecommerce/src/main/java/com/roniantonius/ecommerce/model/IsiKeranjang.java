package com.roniantonius.ecommerce.model;

import java.math.BigDecimal;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
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
@Table(name = "isikeranjang")
@Builder
public class IsiKeranjang {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	
	@Column(nullable = false)
	private int kuantitas;
	
	@Column(nullable = false)
	private BigDecimal hargaUnit;
	
	@Column(nullable = false)
	private BigDecimal hargaTotal;
	
	@ManyToOne
	@JoinColumn(name = "produk_id")
	@Column(nullable = false)
	private Produk produk;
	
	@JsonIgnore
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "keranjang_id", nullable = false)
	private Keranjang keranjang;
	
	public void setHargaTotal() {
		this.hargaTotal = this.hargaUnit.multiply(new BigDecimal(kuantitas));
	};
}
