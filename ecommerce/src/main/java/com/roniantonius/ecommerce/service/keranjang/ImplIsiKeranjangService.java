package com.roniantonius.ecommerce.service.keranjang;

import java.awt.event.ItemEvent;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.roniantonius.ecommerce.exceptions.ResourceNotFoundException;
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
		Keranjang keranjang = implKeranjangService.getKeranjang(keranjangId);
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
		Keranjang keranjang = implKeranjangService.getKeranjang(keranjangId);
		IsiKeranjang isiKeranjang = getIsiKeranjang(keranjangId, produkId);
		keranjang.removeIsiKeranjang(isiKeranjang);
		keranjangRepository.save(keranjang);
	}

	@Override
	public void updateIsiKuantitas(UUID keranjangId, UUID produkId, int kuantitas) {
		// TODO Auto-generated method stub
		Keranjang keranjang = implKeranjangService.getKeranjang(keranjangId);
		keranjang.getIsiKeranjangs().stream()
				.filter(isi -> isi.getProduk().getId().equals(produkId))
				.findFirst()
				.ifPresent(isi -> {
					isi.setHargaUnit(isi.getProduk().getHarga());
					isi.setKuantitas(kuantitas);
					isi.setHargaTotal();
				});
		
		// iterasi ulang untku memperbarui harga keranjang
		BigDecimal jumlahTotal = keranjang.getIsiKeranjangs().stream()
				.map(IsiKeranjang::getHargaTotal)
				.reduce(BigDecimal.ZERO, BigDecimal::add);
		
		keranjang.setJumlahTotal(jumlahTotal);
		keranjangRepository.save(keranjang);
	}
	
	@Override
	public IsiKeranjang getIsiKeranjang(UUID keranjangId, UUID produkId) {
		Keranjang keranjang = implKeranjangService.getKeranjang(keranjangId);
		return keranjang.getIsiKeranjangs().stream()
				.filter(isi -> isi.getProduk().getId().equals(produkId))
				.findFirst().orElseThrow(() -> new ResourceNotFoundException("Produk tidak ditemukan"));
	}
}
