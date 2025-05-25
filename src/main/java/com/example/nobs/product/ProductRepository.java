package com.example.nobs.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.nobs.product.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer>{

}
