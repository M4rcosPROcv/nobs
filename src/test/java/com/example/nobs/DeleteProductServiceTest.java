package com.example.nobs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
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
import com.example.nobs.product.services.DeleteProductService;

public class DeleteProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private DeleteProductService deleteProductService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void given_existing_product_when_delete_then_success() {
        Product product = new Product();
        product.setId(1);
        product.setName("Delete Me");
        product.setDescription("This description is long enough");
        product.setPrice(20.0);

        when(productRepository.findById(1)).thenReturn(Optional.of(product));

        ResponseEntity<Void> response = deleteProductService.execute(1);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(productRepository, times(1)).deleteById(1);
    }

@Test
void given_nonexistent_product_when_delete_then_throw_exception() {
    when(productRepository.findById(1)).thenReturn(Optional.empty());

    assertThrows(ProductNotFoundException.class, () -> deleteProductService.execute(1));
}
}
