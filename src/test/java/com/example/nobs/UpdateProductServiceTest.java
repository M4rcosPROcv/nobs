package com.example.nobs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.nobs.exceptions.ProductNotFoundException;
import com.example.nobs.product.ProductRepository;
import com.example.nobs.product.model.Product;
import com.example.nobs.product.model.ProductDTO;
import com.example.nobs.product.model.UpdateProductCommand;
import com.example.nobs.product.services.UpdateProductService;

public class UpdateProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private UpdateProductService updateProductService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void given_existing_product_when_update_then_return_updated_product_dto() {
        Product existing = new Product();
        existing.setId(1);
        existing.setName("Old Name");
        existing.setDescription("Old description with > 20 characters");
        existing.setPrice(50.0);

        Product updated = new Product();
        updated.setId(1);
        updated.setName("New Name");
        updated.setDescription("New description with > 20 characters");
        updated.setPrice(70.0);

        when(productRepository.findById(1)).thenReturn(Optional.of(existing));
        when(productRepository.save(existing)).thenReturn(updated);

        ResponseEntity<ProductDTO> response = updateProductService.execute(new UpdateProductCommand(1, updated));

        ProductDTO expectedDto = new ProductDTO(updated);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedDto.getName(), response.getBody().getName());
    }

    @Test
    void given_nonexistent_product_when_update_then_throw_exception() {
        Product updated = new Product();
        updated.setId(999);
        updated.setName("Something");
        updated.setDescription("Valid description over 20 chars");
        updated.setPrice(10.0);

        when(productRepository.findById(999)).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> updateProductService.execute(new UpdateProductCommand(999, updated)));
    }
}
