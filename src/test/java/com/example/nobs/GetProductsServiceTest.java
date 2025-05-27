package com.example.nobs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.List;

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
import com.example.nobs.product.services.GetProductsService;

public class GetProductsServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private GetProductsService getProductsService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void when_execute_then_return_all_products_as_dtos() {
        Product p1 = new Product();
        p1.setId(1);
        p1.setName("A");
        p1.setDescription("Description A is long enough");
        p1.setPrice(10.0);

        Product p2 = new Product();
        p2.setId(2);
        p2.setName("B");
        p2.setDescription("Description B is also long enough");
        p2.setPrice(20.0);

        when(productRepository.findAll()).thenReturn(List.of(p1, p2));

        ResponseEntity<List<ProductDTO>> response = getProductsService.execute(null);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        assertEquals("A", response.getBody().get(0).getName());
    }
}
