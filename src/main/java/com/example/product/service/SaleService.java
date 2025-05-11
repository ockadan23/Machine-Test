package com.example.product.service;

import com.example.product.entity.Sale;

import java.util.List;

public interface SaleService {
    Sale markSale(Sale sale);
    List<Sale> getAllSales();
    List<Sale> getSalesByProductId(int productId); 
    Sale getSaleById(int id);
    Sale updateSale(int id, Sale updatedSale);
    void deleteSale(int id);
}