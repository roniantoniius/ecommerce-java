package com.roniantonius.ecommerce.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.roniantonius.ecommerce.model.Pesanan;

public interface PesananRepository extends JpaRepository<Pesanan, UUID>{

}
