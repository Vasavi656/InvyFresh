package com.inventory.inventory_system.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

    public void checkAlerts() {

        List<Product> products = productRepository.findAll();
        LocalDate today = LocalDate.now();

        for (Product p : products) {

            if (p.getStock() <= p.getMinStockLevel()) {

                if (!alertRepository.existsByProductIdAndType(
                        p.getId(), "LOW_STOCK")) {

                    alertRepository.save(
                        new Alert(p.getId(), "LOW_STOCK")
                    );
                }

            } else {
                alertRepository.deleteByProductIdAndType(
                        p.getId(), "LOW_STOCK");
            }

            if (p.getExpiryDate() != null &&
                    !p.getExpiryDate().isBefore(today) &&
                    p.getExpiryDate().isBefore(today.plusDays(7))) {

                if (!alertRepository.existsByProductIdAndType(
                        p.getId(), "EXPIRY")) {

                    alertRepository.save(
                        new Alert(p.getId(), "EXPIRY")
                    );
                }

            } else {
                alertRepository.deleteByProductIdAndType(
                        p.getId(), "EXPIRY");
            }
        }
    }
}
