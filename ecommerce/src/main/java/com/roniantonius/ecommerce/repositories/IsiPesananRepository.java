package com.roniantonius.ecommerce.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.roniantonius.ecommerce.model.IsiPesanan;

public interface IsiPesananRepository extends JpaRepository<IsiPesanan, UUID>{

}
