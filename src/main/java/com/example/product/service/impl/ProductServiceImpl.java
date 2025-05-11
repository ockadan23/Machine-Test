package com.example.product.service.impl;

import com.example.product.entity.Product;
import com.example.product.exception.ProductNotFoundException;
import com.example.product.repository.ProductRepository;
import com.example.product.service.ProductService;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Stream;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public List<Product> getAllProducts(Pageable pageable) {
        log.info("Fetching all products with pagination: {}", pageable);
        return productRepository.findAll(pageable).getContent();
    }

    @Override
    public Product getProductById(int id) {
        log.info("Fetching product with ID: {}", id);
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product with ID " + id + " not found"));
    }

    @Override
    public Product addProduct(Product product) {
        log.info("Adding product: {}", product);
        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(int id, Product updatedProduct) {
        log.info("Updating product with ID: {}", id);
        Product existingProduct = getProductById(id);
        existingProduct.setName(updatedProduct.getName());
        existingProduct.setDescription(updatedProduct.getDescription());
        existingProduct.setPrice(updatedProduct.getPrice());
        existingProduct.setQuantity(updatedProduct.getQuantity());
        return productRepository.save(existingProduct);
    }

    @Override
    public void deleteProduct(int id) {
        log.info("Deleting product with ID: {}", id);
        Product product = getProductById(id);
        productRepository.delete(product);
    }

    @Override
    public double getTotalRevenue() {
        log.info("Calculating total revenue");
        return productRepository.findAll().stream()
                .flatMap(product -> product.getSales().stream())
                .mapToDouble(sale -> sale.getProduct().getPrice().doubleValue() * sale.getQuantity())
                .sum();
    }

    @Override
    public double getRevenueByProduct(int productId) {
        log.info("Calculating revenue for product ID: {}", productId);
        Product product = getProductById(productId);
        return product.getSales().stream()
                .mapToDouble(sale -> product.getPrice().doubleValue() * sale.getQuantity())
                .sum();
    }

    @Override
    public byte[] generateProductPdf() {
        log.info("Generating PDF for product table");
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Document document = new Document();
            PdfWriter.getInstance(document, out);

            document.open();

            // Add title
            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
            Paragraph title = new Paragraph("Product Table", titleFont);
            title.setAlignment(Paragraph.ALIGN_CENTER);
            document.add(title);
            document.add(Chunk.NEWLINE);

            // Create table with 6 columns
            PdfPTable table = new PdfPTable(6);
            table.setWidthPercentage(100);
            table.setWidths(new int[]{1, 3, 5, 2, 2, 3});

            // Add table headers
            Stream.of("ID", "Name", "Description", "Price", "Quantity", "Revenue")
                    .forEach(header -> {
                        PdfPCell cell = new PdfPCell();
                        cell.setPhrase(new Phrase(header, FontFactory.getFont(FontFactory.HELVETICA_BOLD)));
                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                        table.addCell(cell);
                    });

            // Add product data
            List<Product> products = productRepository.findAll();
            for (Product product : products) {
                table.addCell(String.valueOf(product.getId()));
                table.addCell(product.getName());
                table.addCell(product.getDescription());
                table.addCell(String.valueOf(product.getPrice()));
                table.addCell(String.valueOf(product.getQuantity()));

                // Use getRevenueByProduct to calculate revenue
                double revenue = getRevenueByProduct(product.getId());
                table.addCell(String.valueOf(revenue));
            }

            document.add(table);
            document.close();

            return out.toByteArray();
        } catch (Exception e) {
            log.error("Error generating PDF", e);
            throw new RuntimeException("Error generating PDF", e);
        }
    }
}