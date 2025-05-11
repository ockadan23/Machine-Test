package com.example.product;

import com.example.product.entity.Product;
import com.example.product.entity.Sale;
import com.example.product.service.ProductService;
import com.example.product.service.SaleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.math.BigDecimal;
import java.time.LocalDate;

@SpringBootApplication
public class ProductApplication implements CommandLineRunner {

    @Autowired
    private ProductService productService;
    @Autowired
    private SaleService saleService;

    public static void main(String[] args) {
        SpringApplication.run(ProductApplication.class, args);
    }

    @Override
    public void run(String... args) {
        // Product product1 = new Product("Product 1", "Description 1", BigDecimal.valueOf(100), 10);
        // Product product2 = new Product("Product 2", "Description 2", BigDecimal.valueOf(200), 20);

        // productService.addProduct(product1);
        // productService.addProduct(product2);

        // Sale sale1 = new Sale(product1.getId(), 2, LocalDate.now());
        // Sale sale2 = new Sale(product2.getId(), 3, LocalDate.now());

        // saleService.markSale(sale1);
        // saleService.markSale(sale2);
    }
}
