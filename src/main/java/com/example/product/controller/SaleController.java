package com.example.product.controller;

import com.example.product.entity.Sale;
import com.example.product.service.SaleService;

import io.swagger.v3.oas.annotations.Operation;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@RestController
@RequestMapping("api/sales")
@RequiredArgsConstructor
@Slf4j
public class SaleController {

    private final SaleService saleService;

    @PostMapping
    @Operation(summary = "Mark a sale", description = "Record a new sale")
    public ResponseEntity<Sale> markSale(@RequestBody Sale sale) {
        log.info("Marking a new sale: {}", sale);
        return ResponseEntity.status(HttpStatus.CREATED).body(saleService.markSale(sale));
    }

    @GetMapping
    @Operation(summary = "View all sales", description = "Retrieve a list of all sales")
    public ResponseEntity<List<Sale>> viewAllSales() {
        log.info("Fetching all sales");
        return ResponseEntity.ok(saleService.getAllSales());
    }

    @GetMapping("/product/{productId}")
    @Operation(summary = "Get sales by product ID", description = "Retrieve sales associated with a specific product ID")
    public ResponseEntity<List<Sale>> getSalesByProductId(@PathVariable int productId) {
        log.info("Fetching sales for product with ID: {}", productId);
        return ResponseEntity.ok(saleService.getSalesByProductId(productId));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a sale", description = "Update an existing sale")
    public ResponseEntity<Sale> updateSale(@PathVariable int id, @RequestBody Sale updatedSale) {
        log.info("Updating sale with ID: {} with data: {}", id, updatedSale);
        return ResponseEntity.ok(saleService.updateSale(id, updatedSale));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a sale", description = "Delete an existing sale")
    public ResponseEntity<Void> deleteSale(@PathVariable int id) {
        log.info("Deleting sale with ID: {}", id);
        saleService.deleteSale(id);
        return ResponseEntity.noContent().build();
    }
}
