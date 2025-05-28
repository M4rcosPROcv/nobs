package com.example.nobs.product.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.nobs.Query;
import com.example.nobs.product.ProductRepository;
import com.example.nobs.product.model.ProductDTO;

@Service
public class SearchProductService implements Query<String, List<ProductDTO>> {

    private final ProductRepository productRepository;
    private static final Logger logger = LoggerFactory.getLogger(SearchProductService.class);
    
    public SearchProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public ResponseEntity<List<ProductDTO>> execute(String name) {
        logger.info("Executing " + getClass() + " input: " + name);
        return ResponseEntity.ok(productRepository.findByNameOrDescriptionContaining(name)
            .stream()
            .map(ProductDTO::new)
            .toList());
    }

}
