package com.example.product.service;

import com.example.product.entity.Product;

import java.util.List;

import org.springframework.data.domain.Pageable;

public interface ProductService {
    List<Product> getAllProducts(Pageable pageable);
    Product getProductById(int id);
    Product addProduct(Product product);
    Product updateProduct(int id, Product product);
    void deleteProduct(int id);
    double getTotalRevenue();
    double getRevenueByProduct(int productId);
    byte[] generateProductPdf();
}

