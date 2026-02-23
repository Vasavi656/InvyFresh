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
@RequestMapping("/analytics")
public class AnalyticsController {

    @Autowired
    private ProductRepository productRepository;

    @GetMapping
    public String analytics(Model model) {

        List<Product> products = productRepository.findAll();
        LocalDate today = LocalDate.now();

        int lowStock = 0;
        int normal = 0;

        int expired = 0;
        int nearExpiry = 0;
        int safe = 0;

        for (Product p : products) {

            // Stock distribution
            if (p.getStock() <= p.getMinStockLevel()) {
                lowStock++;
            } else {
                normal++;
            }

            // Expiry distribution
            if (p.getExpiryDate() != null) {

                if (p.getExpiryDate().isBefore(today)) {
                    expired++;
                }
                else if (p.getExpiryDate().isBefore(today.plusDays(7))) {
                    nearExpiry++;
                }
                else {
                    safe++;
                }

            } else {
                safe++;
            }
        }

        model.addAttribute("lowStock", lowStock);
        model.addAttribute("normal", normal);

        model.addAttribute("expired", expired);
        model.addAttribute("nearExpiry", nearExpiry);
        model.addAttribute("safe", safe);

        return "analytics";
    }
}