package com.inventory.inventory_system.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.inventory.inventory_system.model.Alert;

public interface AlertRepository extends MongoRepository<Alert, String> {

    List<Alert> findAllByOrderByCreatedAtDesc();

    List<Alert> findByTypeOrderByCreatedAtDesc(String type);

    List<Alert> findByReadFalseOrderByCreatedAtDesc();

    long countByType(String type);

    long countByReadFalse();

    boolean existsByProductIdAndType(String productId, String type);

    void deleteByProductIdAndType(String productId, String type);
}