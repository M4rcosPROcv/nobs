package com.example.nobs.product.services;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CachePut;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.nobs.Command;
import com.example.nobs.exceptions.ProductNotFoundException;
import com.example.nobs.product.ProductRepository;
import com.example.nobs.product.model.Product;
import com.example.nobs.product.model.ProductDTO;
import com.example.nobs.product.model.UpdateProductCommand;
//import com.example.nobs.product.validators.ProductValidator;

@Service
public class UpdateProductService implements Command<UpdateProductCommand, ProductDTO> {

    private final ProductRepository productRepository;
    private static final Logger logger = LoggerFactory.getLogger(UpdateProductService.class);
    
    public UpdateProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    
    @Override
    @CachePut(value = "productCache", key="#command.getId()")
    public ResponseEntity<ProductDTO> execute(UpdateProductCommand command) {
        
        Optional<Product> productOptional = productRepository.findById(command.getId());

        if(productOptional.isPresent()){
            Product product = command.getProduct();
            product.setId(command.getId());
            // ProductValidator.execute(product);
            productRepository.save(product);

            logger.info("Executing " + getClass() + " input: " + command);
            return ResponseEntity.status(HttpStatus.OK).body(new ProductDTO(product));
        }


        throw new ProductNotFoundException();
    }
}
