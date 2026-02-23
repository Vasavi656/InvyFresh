package com.inventory.inventory_system.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.inventory.inventory_system.model.Alert;
import com.inventory.inventory_system.repository.AlertRepository;
import com.inventory.inventory_system.service.AlertService;

@Controller
@RequestMapping("/alerts")
public class AlertUIController {

    @Autowired
    private AlertRepository alertRepository;

    @Autowired
    private AlertService alertService;
    
    @PostMapping("/read/{id}")
    public String markAsRead(@PathVariable String id) {
        Alert alert = alertRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Alert not found"));

        alert.setRead(true);
        alertRepository.save(alert);

        return "redirect:/alerts";
    }

    @GetMapping
public String showAlerts(@RequestParam(defaultValue = "all") String filter,
                         Model model) {

    List<Alert> alerts;
    alertService.checkAlerts();
    switch (filter) {
        case "low":
            alerts = alertRepository.findByTypeOrderByCreatedAtDesc("LOW_STOCK");
            break;

        case "expiry":
            alerts = alertRepository.findByTypeOrderByCreatedAtDesc("EXPIRY");
            break;

        case "unread":
            alerts = alertRepository.findByReadFalseOrderByCreatedAtDesc();
            break;

        default:
            alerts = alertRepository.findAllByOrderByCreatedAtDesc();
    }

    model.addAttribute("alerts", alerts);
    model.addAttribute("activeFilter", filter);

    model.addAttribute("allCount",
            alertRepository.count());

    model.addAttribute("lowCount",
            alertRepository.countByType("LOW_STOCK"));

    model.addAttribute("expiryCount",
            alertRepository.countByType("EXPIRY"));

    model.addAttribute("unreadCount",
            alertRepository.countByReadFalse());

    return "alerts";
}
}
