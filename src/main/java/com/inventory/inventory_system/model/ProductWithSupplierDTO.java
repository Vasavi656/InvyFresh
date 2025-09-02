package com.inventory.inventory_system.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
// this will give product with supplier details init.
public class ProductWithSupplierDTO {
    private String id;
    private String name;
    private String category;
    private double price;
    private int stock;
    private int minStockLevel;
    private Supplier supplier;
}
