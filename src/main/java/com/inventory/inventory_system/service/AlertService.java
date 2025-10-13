package com.inventory.inventory_system.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.inventory.inventory_system.model.Alert;
import com.inventory.inventory_system.model.Product;
import com.inventory.inventory_system.repository.AlertRepository;
import com.inventory.inventory_system.repository.ProductRepository;


@Service
public class AlertService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private AlertRepository alertRepository;

    public void createAlert(String message) {
    if (!alertRepository.existsByMessage(message)) {
        Alert alert = new Alert();
        alert.setMessage(message);
        alert.setCreatedAt(LocalDateTime.now());
        alertRepository.save(alert);
    }
    }
    
    @Scheduled(cron = "0 0 8 * * ?")  
    public void checkAlerts() {
        List<Product> lowStock = productRepository.findAll().stream()
                .filter(p -> p.getStock() <= p.getMinStockLevel())
                .toList();

        List<Product> expiring = productRepository.findAll().stream()
                .filter(p -> p.getExpiryDate() != null &&
                            !p.getExpiryDate().isBefore(LocalDate.now()) &&
                            !p.getExpiryDate().isAfter(LocalDate.now().plusDays(7)))
                .toList();
        for (Product p : lowStock) {
            alertRepository.save(new Alert("⚠ Low stock: " + p.getName() + " has only " + p.getStock() + " items left!"));
        }

        for (Product p : expiring) {
            alertRepository.save(new Alert("⏳ Expiring soon: " + p.getName() + " expires on " + p.getExpiryDate()));
        }

        if (!lowStock.isEmpty() || !expiring.isEmpty()) {
            System.out.println("✅ Alerts saved to MongoDB!");
        }
    }
}