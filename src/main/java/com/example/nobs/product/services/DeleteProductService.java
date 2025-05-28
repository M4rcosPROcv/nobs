package com.example.nobs.product.services;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.nobs.Command;
import com.example.nobs.exceptions.ProductNotFoundException;
import com.example.nobs.product.ProductRepository;
import com.example.nobs.product.model.Product;

@Service
public class DeleteProductService implements Command<Integer, Void> {

    private final ProductRepository productRepository;
    private static final Logger logger = LoggerFactory.getLogger(DeleteProductService.class);

    public DeleteProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public ResponseEntity<Void> execute(Integer id) {
        Optional<Product> productOptional = productRepository.findById(id);

        if (productOptional.isPresent()) {
            productRepository.deleteById(id);

            logger.info("Executing " + getClass() + " input: " + id);

            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        
       throw new ProductNotFoundException();
    }
}
