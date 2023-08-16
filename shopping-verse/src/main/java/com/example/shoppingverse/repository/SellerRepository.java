package com.example.shoppingverse.repository;

import com.example.shoppingverse.model.Seller;
import org.springframework.boot.autoconfigure.jackson.JacksonProperties;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SellerRepository extends JpaRepository<Seller,Integer> {
    public Seller findByEmailId(String email);

}
