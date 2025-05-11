package com.example.product.controller;

import com.example.product.entity.Sale;
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

import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(SaleController.class)
@AutoConfigureMockMvc(addFilters = false) 
class SaleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SaleService saleService;
    @MockBean
    private ProductService productService;

    @Test
    void testMarkSale() throws Exception {
        Sale sale = new Sale(1, 5, LocalDate.now());
        when(saleService.markSale(Mockito.any())).thenReturn(sale);

        mockMvc.perform(post("/api/sales")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"productId\":1,\"quantity\":5,\"saleDate\":\"2025-05-10\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.quantity").value(5));
    }

    @Test
    void testViewAllSales() throws Exception {
        Sale sale = new Sale(1, 5, LocalDate.now());
        when(saleService.getAllSales()).thenReturn(List.of(sale));

        mockMvc.perform(get("/api/sales")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].quantity").value(5));
    }

    @Test
    void testGetSalesByProductId() throws Exception {
        Sale sale = new Sale(1, 5, LocalDate.now());
        when(saleService.getSalesByProductId(1)).thenReturn(List.of(sale));

        mockMvc.perform(get("/api/sales/product/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].quantity").value(5));
    }
}
