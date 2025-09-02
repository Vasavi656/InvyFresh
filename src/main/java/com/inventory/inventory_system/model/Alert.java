package com.inventory.inventory_system.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

// like a table name
@Document(collection = "alerts")  // MongoDB collection name
public class Alert {

    @Id
    private String id;  // MongoDB uses String (ObjectId) for IDs

    private String message;
    private LocalDateTime createdAt;

    // ✅ Default constructor
    public Alert() {}

    // ✅ Constructor with message
    public Alert(String message) {
        this.message = message;
        this.createdAt = LocalDateTime.now();
    }

    // ✅ Getters & Setters
    public String getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
