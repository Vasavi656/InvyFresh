package com.inventory.inventory_system.model;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document("orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    @Id
    private String id;

    private String customerName;
    private String customerEmail;

    private List<String> productIds;
    private double totalAmount;

    private LocalDateTime orderDate; // ✅ renamed to follow Java conventions
}
