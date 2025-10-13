package com.inventory.inventory_system.model;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document("products")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    private String id;
    private String name;
    private String category;
    private double price;
    private int stock;
    private int minStockLevel;
    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDate expiryDate;

}
