package com.example.nobs.product;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.nobs.Command;

@Service
public class UpdateProductService implements Command<Void, String> {
    
    @Override
    public ResponseEntity<String> execute(Void input) {
        return ResponseEntity.status(HttpStatus.OK).body("Product updated");
    }
}
