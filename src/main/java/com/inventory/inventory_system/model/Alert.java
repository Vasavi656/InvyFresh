package com.inventory.inventory_system.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.index.Indexed;

@Document(collection = "alerts")
public class Alert {

    @Id
    private String id;

    private String productId;
    private String type;
    private boolean read = false;

@Indexed(name = "alert_expiry_index", expireAfterSeconds = 2592000)
    private LocalDateTime createdAt;

    public Alert() {}

    public Alert(String productId, String type) {
        this.productId = productId;
        this.type = type;
        this.createdAt = LocalDateTime.now();
        this.read = false;
    }

    public String getId() { return id; }
    public String getProductId() { return productId; }
    public String getType() { return type; }
    public boolean isRead() { return read; }
    public LocalDateTime getCreatedAt() { return createdAt; }

    public void setRead(boolean read) { this.read = read; }
}
