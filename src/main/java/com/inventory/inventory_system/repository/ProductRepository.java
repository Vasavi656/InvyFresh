package com.inventory.inventory_system.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.inventory.inventory_system.model.Product;

public interface ProductRepository extends MongoRepository<Product, String> {

    List<Product> findByStockLessThan(int minStockLevel);

    List<Product> findByNameContainingIgnoreCase(String name);
    
    List<Product> findByCategory(String category);

    List<Product> findByPriceBetween(double min, double max);

    List<Product> findByStockLessThanEqual(int stock);

    List<Product> findByExpiryDateBefore(LocalDate date);

    List<Product> findByExpiryDateBetween(LocalDate startDate, LocalDate endDate);

}
