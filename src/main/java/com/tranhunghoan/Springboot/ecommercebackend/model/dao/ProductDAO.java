package com.tranhunghoan.Springboot.ecommercebackend.model.dao;

import com.tranhunghoan.Springboot.ecommercebackend.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductDAO extends JpaRepository<Product,Long> {
    Optional<Product> findById (Long productId);
}
