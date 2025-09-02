package com.inventory.inventory_system.repository;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.inventory.inventory_system.model.Supplier;

// MongoRepository is an interface so it should be interface
public interface SupplierRepository extends MongoRepository<Supplier,String>{
    
}
