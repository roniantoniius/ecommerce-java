package com.roniantonius.ecommerce.service.pesanan;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.security.web.server.authentication.AnonymousAuthenticationWebFilter;
import org.springframework.stereotype.Service;

import com.roniantonius.ecommerce.enums.StatusPesanan;
import com.roniantonius.ecommerce.exceptions.ResourceNotFoundException;
import com.roniantonius.ecommerce.model.IsiPesanan;
import com.roniantonius.ecommerce.model.Keranjang;
import com.roniantonius.ecommerce.model.Pesanan;
import com.roniantonius.ecommerce.model.Produk;
import com.roniantonius.ecommerce.repositories.PesananRepository;
import com.roniantonius.ecommerce.repositories.ProdukRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ImplPesananService implements PesananService {
	private final PesananRepository pesananRepository;
	private final ProdukRepository produkRepository;
	
	@Override
	public Pesanan taruhPesanan(UUID userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Pesanan getPesanan(UUID pesananId) {
		// TODO Auto-generated method stub
		return pesananRepository.findById(pesananId)
				.orElseThrow(() -> new ResourceNotFoundException("Pesanan tidak ditemukan"));
	}

	@Override
	public BigDecimal calculateJumlahTotalHarga(List<IsiPesanan> daftarIsiPesanans) {
		// TODO Auto-generated method stub
		return daftarIsiPesanans.stream()
				.map(isi -> isi.getHarga().multiply(new BigDecimal(isi.getKuantitas())))
				.reduce(BigDecimal.ZERO, BigDecimal::add);
	}

	@Override
	public List<IsiPesanan> createIsiPesanan(Pesanan pesanan, Keranjang keranjang) {
		// TODO Auto-generated method stub
		return keranjang.getIsiKeranjangs().stream()
			.map(isi -> {
				Produk produk = isi.getProduk();
				produk.setInventory(produk.getInventory() - isi.getKuantitas());
				produkRepository.save(produk);
				return new IsiPesanan(
						pesanan,
						produk,
						isi.getKuantitas(),
						isi.getHargaUnit());
			}).toList();
	}

	@Override
	public Pesanan createPesanan(Keranjang keranjang) {
		// TODO Auto-generated method stub
		// banyak pesanan dimilliki oleh satu user
		Pesanan pesanan = new Pesanan();
		pesanan.setStatusPesanan(StatusPesanan.PENDING);
		pesanan.setPesananWaktu(LocalDate.now());
		return pesanan;
	}
}
