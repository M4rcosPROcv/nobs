package com.example.nobs.product.services;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.nobs.Command;
import com.example.nobs.exceptions.ProductNotFoundException;
import com.example.nobs.product.ProductRepository;
import com.example.nobs.product.model.Product;
import com.example.nobs.product.model.ProductDTO;
import com.example.nobs.product.model.UpdateProductCommand;

@Service
public class UpdateProductService implements Command<UpdateProductCommand, ProductDTO> {

    private final ProductRepository productRepository;
    
    public UpdateProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    
    @Override
    public ResponseEntity<ProductDTO> execute(UpdateProductCommand command) {
        
        Optional<Product> productOptional = productRepository.findById(command.getId());

        if(productOptional.isPresent()){
            Product product = command.getProduct();
            product.setId(command.getId());
            productRepository.save(product);
            return ResponseEntity.status(HttpStatus.OK).body(new ProductDTO(product));
        }


        throw new ProductNotFoundException();
    }
}
