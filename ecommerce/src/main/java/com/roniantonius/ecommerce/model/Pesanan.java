package com.roniantonius.ecommerce.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

import com.roniantonius.ecommerce.enums.StatusPesanan;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = "pesanan")
@Builder
public class Pesanan {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	
	@Column(nullable = false)
	private LocalDate pesananWaktu;
	
	@Column(nullable = false)
	private BigDecimal jumlahTotal;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private StatusPesanan statusPesanan;
	
	@OneToMany(mappedBy = "pesanan", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<IsiPesanan> isiPesanans;
}