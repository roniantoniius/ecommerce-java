package com.roniantonius.ecommerce.model;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "keranjang")
public class Keranjang {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	
	@Column(nullable = false)
	private BigDecimal jumlahTotal = BigDecimal.ZERO;
	
	@OneToMany(mappedBy = "keranjang", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<IsiKeranjang> isiKeranjangs = new HashSet<>();
	
	public void addIsiKeranjang(IsiKeranjang isi) {
		this.isiKeranjangs.add(isi);
		isi.setKeranjang(this);
		updateJumlahTotal();
	}
	
	public void removeIsiKeranjang(IsiKeranjang isi) {
		this.isiKeranjangs.remove(isi);
		isi.setKeranjang(null);
		updateJumlahTotal();
	}
	
	public void updateJumlahTotal() {
		this.jumlahTotal = isiKeranjangs.stream()
				.map(isi -> {
					BigDecimal hargaUnit = isi.getHargaUnit();
					if (hargaUnit == null) {
						return BigDecimal.ZERO;
					}
					return hargaUnit.multiply(BigDecimal.valueOf(isi.getKuantitas()));
				}).reduce(BigDecimal.ZERO, BigDecimal::add);
	}
}
