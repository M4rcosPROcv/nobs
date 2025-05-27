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
import com.example.nobs.product.services.SearchProductService;

public class SearchProductServiceTest {
    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private SearchProductService searchProductService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void given_search_term_when_execute_then_return_matching_products() {
        Product p = new Product();
        p.setId(1);
        p.setName("Keyboard");
        p.setDescription("Good mechanical keyboard");
        p.setPrice(50.0);

        when(productRepository.findByNameOrDescriptionContaining("key")).thenReturn(List.of(p));

        ResponseEntity<List<ProductDTO>> response = searchProductService.execute("key");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals("Keyboard", response.getBody().get(0).getName());
    }
}
