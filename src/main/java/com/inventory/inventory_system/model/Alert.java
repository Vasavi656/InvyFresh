package com.inventory.inventory_system.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "alerts")  
public class Alert {
    
    @Id
    private String id; 
    private String message;
    private LocalDateTime createdAt;

    
    public Alert() {}

    
    public Alert(String message) {
        this.message = message;
        this.createdAt = LocalDateTime.now();
    }

    
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
