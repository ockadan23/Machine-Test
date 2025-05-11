package com.example.product.controller;

import com.example.product.entity.Product;
import com.example.product.service.ProductService;
import com.example.product.service.SaleService;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
@AutoConfigureMockMvc(addFilters = false) 
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;
    @MockBean
    private SaleService saleService;

    @Test
    void testGetAllProducts() throws Exception {
        Product product = new Product("Product1", "Description1", BigDecimal.valueOf(100), 10);
        when(productService.getAllProducts(Mockito.any())).thenReturn(List.of(product));

        mockMvc.perform(get("/api/products")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Product1"));
    }

    @Test
    void testGetProductById() throws Exception {
        Product product = new Product("Product1", "Description1", BigDecimal.valueOf(100), 10);
        when(productService.getProductById(1)).thenReturn(product);

        mockMvc.perform(get("/api/products/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Product1"));
    }

    @Test
    void testAddProduct() throws Exception {
        Product product = new Product("Product1", "Description1", BigDecimal.valueOf(100), 10);
        when(productService.addProduct(Mockito.any())).thenReturn(product);

        mockMvc.perform(post("/api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Product1\",\"description\":\"Description1\",\"price\":100,\"quantity\":10}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Product1"));
    }

    @Test
    void testUpdateProduct() throws Exception {
        Product product = new Product("Product1", "Description1", BigDecimal.valueOf(100), 10);
        when(productService.updateProduct(Mockito.eq(1), Mockito.any())).thenReturn(product);

        mockMvc.perform(put("/api/products/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Product1\",\"description\":\"Description1\",\"price\":100,\"quantity\":10}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Product1"));
    }

    @Test
    void testDeleteProduct() throws Exception {
        mockMvc.perform(delete("/api/products/1"))
                .andExpect(status().isNoContent());
    }
}