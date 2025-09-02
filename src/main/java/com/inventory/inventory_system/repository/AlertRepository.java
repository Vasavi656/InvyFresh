package com.inventory.inventory_system.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.inventory.inventory_system.model.Alert;

public interface AlertRepository extends MongoRepository<Alert, String> {
    // later we can add custom queries if needed
     boolean existsByMessage(String message);
}
