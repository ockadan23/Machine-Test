package com.example.product.service.impl;

import com.example.product.entity.Sale;
import com.example.product.exception.SaleNotFoundException;
import com.example.product.repository.SaleRepository;
import com.example.product.service.SaleService;
import com.example.product.entity.Product;
import com.example.product.repository.ProductRepository;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class SaleServiceImpl implements SaleService {

    private final SaleRepository saleRepository;
    private final ProductRepository productRepository;

    @Override
    public Sale markSale(Sale sale) {
        log.info("Marking sale: {}", sale);
        Product product = productRepository.findById(sale.getProductId())
                .orElseThrow(() -> new IllegalArgumentException("Product with ID " + sale.getProductId() + " not found"));

        if (product.getQuantity() < sale.getQuantity()) {
            log.error("Insufficient quantity for product with ID: {}", sale.getProductId());
            throw new IllegalArgumentException("Insufficient quantity for product with ID " + sale.getProductId());
        }

        product.setQuantity(product.getQuantity() - sale.getQuantity());
        productRepository.save(product);
        log.info("Updated product quantity for product ID: {}", sale.getProductId());

        return saleRepository.save(sale);
    }

    @Override
    public List<Sale> getAllSales() {
        log.info("Fetching all sales");
        return saleRepository.findAll();
    }

    @Override
    public List<Sale> getSalesByProductId(int productId) {
        log.info("Fetching sales for product ID: {}", productId);
        return saleRepository.findByProductId(productId);
    }

    @Override
    public Sale updateSale(int id, Sale updatedSale) {
        log.info("Updating sale with ID: {}", id);
        Sale existingSale = getSaleById(id);

        Product product = productRepository.findById(existingSale.getProductId())
                .orElseThrow(() -> new IllegalArgumentException("Product with ID " + existingSale.getProductId() + " not found"));

        int quantityDifference = updatedSale.getQuantity() - existingSale.getQuantity();
        if (product.getQuantity() < quantityDifference) {
            log.error("Insufficient quantity for product with ID: {}", product.getId());
            throw new IllegalArgumentException("Insufficient quantity for product with ID " + product.getId());
        }

        product.setQuantity(product.getQuantity() - quantityDifference);
        productRepository.save(product);
        log.info("Updated product quantity for product ID: {}", product.getId());

        existingSale.setQuantity(updatedSale.getQuantity());
        existingSale.setSaleDate(updatedSale.getSaleDate());
        existingSale.setProductId(updatedSale.getProductId());

        return saleRepository.save(existingSale);
    }

    @Override
    public Sale getSaleById(int id) {
        log.info("Fetching sale with ID: {}", id);
        return saleRepository.findById(id)
                .orElseThrow(() -> new SaleNotFoundException("Sale with ID " + id + " not found"));
    }

    @Override
    public void deleteSale(int id) {
        log.info("Deleting sale with ID: {}", id);
        Sale sale = getSaleById(id);
        saleRepository.delete(sale);
    }
}