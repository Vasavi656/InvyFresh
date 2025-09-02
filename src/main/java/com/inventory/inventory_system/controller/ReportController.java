package com.inventory.inventory_system.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.inventory.inventory_system.model.Order;
import com.inventory.inventory_system.repository.OrderRepository;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    @Autowired
    private OrderRepository orderRepository;

    // Get orders between two dates
    @GetMapping("/orders")
    public List<Order> getOrdersBetween(
        @RequestParam LocalDate startDate,
        @RequestParam LocalDate endDate
    ) {
        LocalDateTime start = startDate.atStartOfDay();
        LocalDateTime end = endDate.atTime(23, 59, 59);
        return orderRepository.findByOrderDateBetween(start, end);
    }

    // Get total revenue in a date range
    @GetMapping("/revenue")
    public double getRevenueBetween(
        @RequestParam LocalDate startDate,
        @RequestParam LocalDate endDate
    ) {
        LocalDateTime start = startDate.atStartOfDay();
        LocalDateTime end = endDate.atTime(23, 59, 59);
        List<Order> orders = orderRepository.findByOrderDateBetween(start, end);
        return orders.stream().mapToDouble(Order::getTotalAmount).sum();
    }

    // Get today's total orders count
    @GetMapping("/today-orders")
    public long getTodayOrders() {
        LocalDateTime start = LocalDate.now().atStartOfDay();
        LocalDateTime end = LocalDateTime.now();
        return orderRepository.findByOrderDateBetween(start, end).size();
    }
    @GetMapping("/tomorrow-orders")
    public long getTomorrowOrders() {
        LocalDate tomorrow = LocalDate.now().plusDays(1);
        LocalDateTime start = tomorrow.atStartOfDay();
        LocalDateTime end = tomorrow.plusDays(1).atStartOfDay();
        return orderRepository.findByOrderDateBetween(start, end).size();
    }

    @GetMapping("/weekly-orders")
    public long getWeeklyOrders() {
        LocalDateTime start = LocalDate.now().minusDays(6).atStartOfDay();
        LocalDateTime end = LocalDateTime.now();
        return orderRepository.findByOrderDateBetween(start, end).size();
    }

    @GetMapping("/monthly-orders")
    public long getMonthlyOrders() {
        LocalDateTime start = LocalDate.now().withDayOfMonth(1).atStartOfDay();
        LocalDateTime end = LocalDateTime.now();
        return orderRepository.findByOrderDateBetween(start, end).size();
    }

    @GetMapping("/top-products")
    public Map<String, Long> getTopProducts() {
        List<Order> allOrders = orderRepository.findAll();

        // Count frequency of each productId
        Map<String, Long> productCount = allOrders.stream()
                .flatMap(order -> order.getProductIds().stream())
                .collect(Collectors.groupingBy(pid -> pid, Collectors.counting()));

        // Sort by highest sales count
        return productCount.entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .limit(5) // Top 5
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));
    }
}
