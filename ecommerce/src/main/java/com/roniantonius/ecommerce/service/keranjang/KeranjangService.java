package com.roniantonius.ecommerce.service.keranjang;

import java.math.BigDecimal;
import java.util.UUID;

import com.roniantonius.ecommerce.model.Keranjang;

public interface KeranjangService {
	Keranjang getKeranjang(UUID id);
	void clearKeranjang(UUID id);
	BigDecimal getHargaTotal(UUID id);
}
