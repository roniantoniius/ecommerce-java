package com.roniantonius.ecommerce.service.keranjang;

import java.util.UUID;

public interface IsiKeranjangService {
	void addIsiToKeranjang(UUID keranjangId, UUID produkId, int kuantitas);
	void removeIsiFromKeranjang(UUID keranjangId, UUID produkId);
	void updateIsiKuantitas(UUID keranjangId, UUID produkId, int kuantitas);
}
