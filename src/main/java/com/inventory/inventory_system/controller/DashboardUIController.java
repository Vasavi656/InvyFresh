package com.inventory.inventory_system.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.inventory.inventory_system.model.Alert;
import com.inventory.inventory_system.model.Product;
import com.inventory.inventory_system.repository.AlertRepository;
import com.inventory.inventory_system.repository.ProductRepository;

@Controller
public class DashboardUIController {

    @Autowired
    private AlertRepository alertRepository;

    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/dashboard")
public String dashboard(Model model) {
    // Fetch alerts
    List<Alert> alerts = alertRepository.findAll();
    model.addAttribute("alerts", alerts);

    // Fetch products for charts
    List<Product> products = productRepository.findAll();

    // Stock chart data
    List<String> stockLabels = products.stream()
        .map(Product::getName)
        .toList();

    List<Integer> stockValues = products.stream()
        .map(Product::getStock)
        .toList();

    model.addAttribute("stockLabels", stockLabels);
    model.addAttribute("stockValues", stockValues);

    // Expiry chart data
    LocalDate today = LocalDate.now();

    long expiringSoon = products.stream()
        .filter(p -> !p.getExpiryDate().isBefore(today) &&
                     p.getExpiryDate().isBefore(today.plusDays(7)))
        .count();

    long expired = products.stream()
        .filter(p -> p.getExpiryDate().isBefore(today))
        .count();

    long fresh = products.size() - expiringSoon - expired;

    model.addAttribute("expiryLabels", List.of("Expiring Soon", "Fresh", "Expired"));
    model.addAttribute("expiryValues", List.of(expiringSoon, fresh, expired));

    return "dashboard";
}

}

