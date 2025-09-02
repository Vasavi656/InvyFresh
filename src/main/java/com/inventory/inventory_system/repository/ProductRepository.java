package com.inventory.inventory_system.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.inventory.inventory_system.model.Product;

// monogorepository is used for direct monogodb sql queries
public interface ProductRepository extends MongoRepository<Product, String> {

    // this give the products that are less than minstocklevel
    List<Product> findByStockLessThan(int minStockLevel);

    // Search by name containing keyword (case-insensitive)
    List<Product> findByNameContainingIgnoreCase(String name);
    
    // Filter by category
    List<Product> findByCategory(String category);

    // Filter by price range
    List<Product> findByPriceBetween(double min, double max);
    // ✅ Find products with stock less than or equal to given level
    List<Product> findByStockLessThanEqual(int stock);

    // ✅ Find products expiring before a certain date
    List<Product> findByExpiryDateBefore(LocalDate date);

    // ✅ Find products expiring between two dates
    List<Product> findByExpiryDateBetween(LocalDate startDate, LocalDate endDate);

}
