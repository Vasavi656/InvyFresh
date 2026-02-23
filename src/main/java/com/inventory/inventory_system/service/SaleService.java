package com.inventory.inventory_system.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.inventory.inventory_system.model.Product;
import com.inventory.inventory_system.model.Sale;
import com.inventory.inventory_system.model.Transaction;
import com.inventory.inventory_system.repository.ProductRepository;
import com.inventory.inventory_system.repository.SaleRepository;
import com.inventory.inventory_system.repository.TransactionRepository;

@Service
public class SaleService {
    @Autowired
    private SaleRepository saleRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private AlertService alertService;

    @Autowired
    private TransactionRepository transactionRepository;
public String processSale(Sale sale) {

    Product product = productRepository.findById(sale.getProductId())
            .orElseThrow(() -> new RuntimeException("Product not found"));

    if (product.getExpiryDate() != null &&
            product.getExpiryDate().isBefore(LocalDate.now())) {

        saveFailedTransaction(sale.getQuantitySold());

        throw new RuntimeException("Cannot sell expired product.");
    }

    if (sale.getQuantitySold() <= 0 ||
            sale.getQuantitySold() > product.getStock()) {

        saveFailedTransaction(sale.getQuantitySold());

        throw new RuntimeException("Invalid sale quantity.");
    }

    double totalAmount = product.getPrice() * sale.getQuantitySold();

    if (totalAmount > 10000) {

        saveFailedTransaction(sale.getQuantitySold());

        throw new RuntimeException("Payment declined. Amount too high.");
    }

    sale.setSoldAt(LocalDateTime.now());
    Sale savedSale = saleRepository.save(sale);

    product.setStock(product.getStock() - sale.getQuantitySold());
    productRepository.save(product);
    alertService.checkAlerts(); 
    String token = UUID.randomUUID().toString();

    Transaction successTransaction =
            new Transaction(savedSale.getId(),
                    token,
                    "SUCCESS",
                    sale.getQuantitySold());

    transactionRepository.save(successTransaction);

    return "Sale successful. Transaction ID: " + token;
}
private void saveFailedTransaction(int quantity) {

    String token = UUID.randomUUID().toString();

    Transaction failedTransaction =
            new Transaction(null,
                    token,
                    "FAILED",
                    quantity);

    transactionRepository.save(failedTransaction);
}
}
