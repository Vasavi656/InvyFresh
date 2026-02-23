package com.inventory.inventory_system.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.inventory.inventory_system.model.Product;
import com.inventory.inventory_system.repository.ProductRepository;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {

    @Autowired
    private ProductRepository productRepository;

    @GetMapping
    public String dashboard(Model model) {

        List<Product> products = productRepository.findAll();
        LocalDate today = LocalDate.now();

        long totalProducts = products.size();

        long lowStock = products.stream()
                .filter(p -> p.getStock() <= p.getMinStockLevel())
                .count();

        long nearExpiry = products.stream()
                .filter(p -> p.getExpiryDate() != null &&
                        !p.getExpiryDate().isBefore(today) &&
                        p.getExpiryDate().isBefore(today.plusDays(7)))
                .count();

        long expiredProducts = products.stream()
                .filter(p -> p.getExpiryDate() != null &&
                        p.getExpiryDate().isBefore(today))
                .count();

        model.addAttribute("totalProducts", totalProducts);
        model.addAttribute("lowStock", lowStock);
        model.addAttribute("nearExpiry", nearExpiry);
        model.addAttribute("expiredProducts", expiredProducts);

        return "dashboard";
    }
}