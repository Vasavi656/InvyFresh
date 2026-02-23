package com.inventory.inventory_system.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.inventory.inventory_system.model.Alert;
import com.inventory.inventory_system.repository.AlertRepository;

@RestController
@RequestMapping("/api/alerts")

public class AlertController {

    @Autowired
    private AlertRepository alertRepository;

    @GetMapping
    public List<Alert> getAllAlerts() {
        return alertRepository.findAllByOrderByCreatedAtDesc();
    }

    @GetMapping("/unread")
    public List<Alert> getUnreadAlerts() {
        return alertRepository.findAllByOrderByCreatedAtDesc()
                .stream()
                .filter(a -> !a.isRead())
                .toList();
    }


    @PutMapping("/{id}/read")
    public String markAsRead(@PathVariable String id) {

        Alert alert = alertRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Alert not found"));

        alert.setRead(true);
        alertRepository.save(alert);

        return "Alert marked as read";
    }


    @DeleteMapping("/{id}")
    public String deleteAlert(@PathVariable String id) {

        alertRepository.deleteById(id);

        return "Alert deleted successfully";
    }
}
