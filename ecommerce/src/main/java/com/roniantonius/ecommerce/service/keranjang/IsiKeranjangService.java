package com.roniantonius.ecommerce.service.keranjang;

import java.util.UUID;

import com.roniantonius.ecommerce.model.IsiKeranjang;

public interface IsiKeranjangService {
	void addIsiToKeranjang(UUID keranjangId, UUID produkId, int kuantitas);
	void removeIsiFromKeranjang(UUID keranjangId, UUID produkId);
	void updateIsiKuantitas(UUID keranjangId, UUID produkId, int kuantitas);
	IsiKeranjang getIsiKeranjang(UUID keranjangId, UUID produkId);
}
