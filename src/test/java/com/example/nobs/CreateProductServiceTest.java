package com.example.nobs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.nobs.product.ProductRepository;
import com.example.nobs.product.model.Product;
import com.example.nobs.product.model.ProductDTO;
import com.example.nobs.product.services.CreateProductService;

public class CreateProductServiceTest {

     @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private CreateProductService createProductService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void given_valid_product_dto_when_create_product_then_save_and_return_product_dto() {
    
        // Given: the product to be saved
        Product productToSave = new Product();
        productToSave.setName("Test Product");
        productToSave.setDescription("This is a test product description");
        productToSave.setPrice(99.99);

        // Simulate the saved product with an assigned ID
        Product savedProduct = new Product();
        savedProduct.setId(1);
        savedProduct.setName(productToSave.getName());
        savedProduct.setDescription(productToSave.getDescription());
        savedProduct.setPrice(productToSave.getPrice());

        when(productRepository.save(any(Product.class))).thenReturn(savedProduct);

        // When
        ResponseEntity<ProductDTO> response = createProductService.execute(productToSave);

        // Then
        ProductDTO expectedDto = new ProductDTO(savedProduct);
        assertEquals(ResponseEntity.status(HttpStatus.CREATED).body(expectedDto), response);
        verify(productRepository, times(1)).save(any(Product.class));
       
    }
}
