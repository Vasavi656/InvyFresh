package com.inventory.inventory_system.model;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
// to map this class to mongodb
@Document("products")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    // primary key
    @Id
    private String id;
    private String name;
    private String category;
    private double price;
    private int stock;
    private int minStockLevel;
    // private String supplierId;
    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDate expiryDate;

}
