package com.inventory.inventory_system.model;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderWithProductDTO {
    private String id;
    private String customerName;
    private String customerEmail;
    private List<Product> products;  // full product objects
    private double totalAmount;
    private LocalDateTime orderDate;
}
