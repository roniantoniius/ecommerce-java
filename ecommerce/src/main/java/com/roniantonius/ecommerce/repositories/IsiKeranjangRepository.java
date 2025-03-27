package com.roniantonius.ecommerce.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.roniantonius.ecommerce.model.IsiKeranjang;

public interface IsiKeranjangRepository extends JpaRepository<IsiKeranjang, UUID>{

	void deleteAllByKeranjangId(UUID id);

}
