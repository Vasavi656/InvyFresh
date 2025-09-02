package com.inventory.inventory_system.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.inventory.inventory_system.model.Order;
import com.inventory.inventory_system.model.Product;
import com.inventory.inventory_system.repository.OrderRepository;
import com.inventory.inventory_system.repository.ProductRepository;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {
    @Autowired 
    private ProductRepository productRepo;
    @Autowired
    private OrderRepository orderRepo;
    // ✅ Total products
    @GetMapping("/total-products")
    public long getTotalProducts() {
        return productRepo.count();
    }

    // ✅ Total orders
    @GetMapping("/total-orders")
    public long getTotalOrders() {
        return orderRepo.count();
    }

    // ✅ Total revenue
    @GetMapping("/total-revenue")
    public double getTotalRevenue() {
        List<Order> orders = orderRepo.findAll();
        return orders.stream().mapToDouble(Order::getTotalAmount).sum();
    }

    // ✅ Low stock products
    @GetMapping("/low-stock")
    public List<Product> getLowStock(@RequestParam(defaultValue = "10") int min) {
        return productRepo.findByStockLessThan(min);
    }
}

