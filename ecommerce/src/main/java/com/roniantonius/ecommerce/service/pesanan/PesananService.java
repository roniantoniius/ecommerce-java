package com.roniantonius.ecommerce.service.pesanan;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import com.roniantonius.ecommerce.model.IsiPesanan;
import com.roniantonius.ecommerce.model.Keranjang;
import com.roniantonius.ecommerce.model.Pesanan;

public interface PesananService {
	Pesanan taruhPesanan(UUID userId);
	Pesanan getPesanan(UUID pesananId);
	BigDecimal calculateJumlahTotalHarga(List<IsiPesanan> daftarIsiPesanans);
	List<IsiPesanan> createIsiPesanan(Pesanan pesanan, Keranjang keranjang);
	Pesanan createPesanan(Keranjang keranjang);
}
