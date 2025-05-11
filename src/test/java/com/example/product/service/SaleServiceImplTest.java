package com.example.product.service;

import com.example.product.entity.Sale;
import com.example.product.exception.SaleNotFoundException;
import com.example.product.repository.SaleRepository;
import com.example.product.service.impl.SaleServiceImpl;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SaleServiceImplTest {

    @Mock
    private SaleRepository saleRepository;

    @InjectMocks
    private SaleServiceImpl saleService;

    public SaleServiceImplTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetSaleById() {
        Sale sale = new Sale(1, 5, LocalDate.now());
        when(saleRepository.findById(1)).thenReturn(Optional.of(sale));

        Sale result = saleService.getSaleById(1);
        assertEquals(5, result.getQuantity());
    }

    @Test
    void testGetSaleById_NotFound() {
        when(saleRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(SaleNotFoundException.class, () -> saleService.getSaleById(1));
    }
}
