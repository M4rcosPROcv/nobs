package com.example.nobs.product.services;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.nobs.Query;
import com.example.nobs.product.ProductRepository;
import com.example.nobs.product.model.ProductDTO;

@Service
public class SearchProductService implements Query<String, List<ProductDTO>> {

    private final ProductRepository productRepository;
    public SearchProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public ResponseEntity<List<ProductDTO>> execute(String name) {
        return ResponseEntity.ok(productRepository.findByNameOrDescriptionContaining(name)
            .stream()
            .map(ProductDTO::new)
            .toList());
    }

}
