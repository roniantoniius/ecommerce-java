package com.roniantonius.ecommerce.service.keranjang;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.roniantonius.ecommerce.model.IsiKeranjang;
import com.roniantonius.ecommerce.model.Keranjang;
import com.roniantonius.ecommerce.model.Produk;
import com.roniantonius.ecommerce.repositories.IsiKeranjangRepository;
import com.roniantonius.ecommerce.repositories.KeranjangRepository;
import com.roniantonius.ecommerce.service.produk.ImplProdukService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ImplIsiKeranjangService implements IsiKeranjangService {
	private final IsiKeranjangRepository isiKeranjangRepository;
	private final KeranjangRepository keranjangRepository;
	private final ImplProdukService implProdukService;
	private final ImplKeranjangService implKeranjangService;
	
	@Transactional
	@Override
	public void addIsiToKeranjang(UUID keranjangId, UUID produkId, int kuantitas) {
		// 1. Ambil cart
		// 2. Ambil produk
		// 3. Periksa si produk ada atau ngga di isi Keranjang melalui id produk, 
		// 4. Kalau ada produk di isiKeranjang maka kita cuma perbarui kuantitas
		// 5. Kalau ngga ada produk di IsiKeranjang maka kita bikin IsiKeranjang yang baru dengan isi produkId tersebut
		// 6. Setelah memperbarui isiKeranjang, otomatis kita perlu memperbarui HargaTotal, dan tambah isi Keranjang ke keranjang.
		// TODO Auto-generated method stub
		Keranjang keranjang = implKeranjangService.getKeranjang(produkId);
		Produk produk = implProdukService.getProdukById(produkId);
		IsiKeranjang isiKeranjang = keranjang.getIsiKeranjangs().stream()
				.filter(isi -> isi.getProduk().getId().equals(produkId))
				.findFirst().orElse(new IsiKeranjang());
		if (isiKeranjang == null) {
			isiKeranjang.setKeranjang(keranjang);
			isiKeranjang.setProduk(produk);
			isiKeranjang.setKuantitas(kuantitas);
			isiKeranjang.setHargaUnit(produk.getHarga());
		} else {
			isiKeranjang.setKuantitas(isiKeranjang.getKuantitas() + kuantitas);
		}
		
		// karena kuantitas dan isi sudah baru, maka total harus diperbarui
		isiKeranjang.setHargaTotal();
		keranjang.addIsiKeranjang(isiKeranjang);
		isiKeranjangRepository.save(isiKeranjang);
		keranjangRepository.save(keranjang);
	}

	@Override
	public void removeIsiFromKeranjang(UUID keranjangId, UUID produkId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateIsiKuantitas(UUID keranjangId, UUID produkId, int kuantitas) {
		// TODO Auto-generated method stub
		
	}

}
