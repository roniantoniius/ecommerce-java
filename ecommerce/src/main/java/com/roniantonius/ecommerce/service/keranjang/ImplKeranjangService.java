package com.roniantonius.ecommerce.service.keranjang;

import java.math.BigDecimal;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.roniantonius.ecommerce.exceptions.ResourceNotFoundException;
import com.roniantonius.ecommerce.model.IsiKeranjang;
import com.roniantonius.ecommerce.model.Keranjang;
import com.roniantonius.ecommerce.repositories.IsiKeranjangRepository;
import com.roniantonius.ecommerce.repositories.KeranjangRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ImplKeranjangService implements KeranjangService {

	private final KeranjangRepository keranjangRepository;
	private final IsiKeranjangRepository isiKeranjangRepository;
	
	@Override
	public Keranjang getKeranjang(UUID id) {
		// TODO Auto-generated method stub
		Keranjang keranjang = keranjangRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Keranjang tidak ditemukan"));
		BigDecimal jumlahTotal = keranjang.getJumlahTotal();
		keranjang.setJumlahTotal(jumlahTotal);
		return keranjangRepository.save(keranjang);
	}

	@Override
	public void clearKeranjang(UUID id) {
		// TODO Auto-generated method stub
		Keranjang keranjang = getKeranjang(id);
		isiKeranjangRepository.deleteAllByKeranjangId(id);
		keranjang.getIsiKeranjangs().clear();
		keranjangRepository.deleteById(id);
	}

	@Override
	public BigDecimal getHargaTotal(UUID id) {
		// TODO Auto-generated method stub
		Keranjang keranjang = getKeranjang(id);
		return keranjang.getJumlahTotal();
	}

}
