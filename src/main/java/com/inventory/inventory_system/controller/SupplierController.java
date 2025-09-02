package com.inventory.inventory_system.controller;

import com.inventory.inventory_system.model.Supplier;
import com.inventory.inventory_system.repository.SupplierRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/suppliers")
public class SupplierController {

    @Autowired
    private SupplierRepository supplierRepo;

    @GetMapping
    public List<Supplier> getAllSuppliers() {
        return supplierRepo.findAll();
    }

    @GetMapping("/{id}")
    public Supplier getSupplierById(@PathVariable String id) {
        return supplierRepo.findById(id).orElseThrow(() -> new RuntimeException("Supplier not found"));
    }

    @PostMapping
    public Supplier addSupplier(@RequestBody Supplier supplier) {
        return supplierRepo.save(supplier);
    }

    @PutMapping("/{id}")
    public Supplier updateSupplier(@PathVariable String id, @RequestBody Supplier updated) {
        return supplierRepo.findById(id)
                .map(existing -> {
                    existing.setName(updated.getName());
                    existing.setEmail(updated.getEmail());
                    existing.setPhone(updated.getPhone());
                    existing.setAddress(updated.getAddress());
                    return supplierRepo.save(existing);
                }).orElseThrow(() -> new RuntimeException("Supplier not found"));
    }

    @DeleteMapping("/{id}")
    public String deleteSupplier(@PathVariable String id) {
        supplierRepo.deleteById(id);
        return "Supplier deleted successfully";
    }
}
