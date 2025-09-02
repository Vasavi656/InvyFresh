package com.inventory.inventory_system.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.inventory.inventory_system.model.Order;

// interface can extends another interface
public interface OrderRepository extends MongoRepository<Order, String>{
    List<Order> findByOrderDateBetween(LocalDateTime start, LocalDateTime end);
    @Query("{ 'createdAt': { $gte: ?0, $lt: ?1 } }")
    List<Order> findOrdersBetweenDates(LocalDateTime start, LocalDateTime end);
}
