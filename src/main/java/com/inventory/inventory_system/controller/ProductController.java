package com.inventory.inventory_system.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.inventory.inventory_system.model.Product;
import com.inventory.inventory_system.repository.ProductRepository;
// import com.inventory.inventory_system.repository.SupplierRepository;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    @Autowired
    private ProductRepository productRepository;

    // @Autowired
    // private SupplierRepository supplierRepository;
    @GetMapping
    public List<Product> getAllProducts(){
        return productRepository.findAll();
    } 

    @GetMapping("/{id}")
    public Product getProductById(@PathVariable  String id){
        return productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
    }

    @PostMapping
    public void postProduct(@RequestBody Product prod){
        productRepository.save(prod);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable String id){
        productRepository.deleteById(id);
    }

    @PutMapping("/{id}")
    public Product putProduct(@PathVariable String id,@RequestBody Product updatedProduct){
        return productRepository.findById(id).map(prod -> {
            prod.setName(updatedProduct.getName());
                prod.setCategory(updatedProduct.getCategory());
                prod.setPrice(updatedProduct.getPrice());
                prod.setStock(updatedProduct.getStock());
                prod.setMinStockLevel(updatedProduct.getMinStockLevel());
                // prod.setSupplierId(updatedProduct.getSupplierId());
                return productRepository.save(prod);
        }).orElse(null);
    }

    @GetMapping("/search")
    public List<Product> searchByName(@RequestParam String name) {
        return productRepository.findByNameContainingIgnoreCase(name);
    }

    @GetMapping("/category")
    public List<Product> getByCategory(@RequestParam String category) {
        return productRepository.findByCategory(category);
    }

    @GetMapping("/price-range")
    public List<Product> getByPriceRange(@RequestParam double min, @RequestParam double max) {
        return productRepository.findByPriceBetween(min, max);
    }

    // @GetMapping("/{id}/with-supplier")
    // public ProductWithSupplierDTO getProductWithSupplier(@PathVariable String id){
    //     Product product =productRepository.findById(id).orElseThrow(()->new RuntimeException("product not found"));
    //     Supplier supplier=null;
    //     if(product.getSupplierId()!=null){
    //         supplier=supplierRepository.findById(product.getSupplierId()).orElse(null);
    //     }
    //     return new ProductWithSupplierDTO(
    //         product.getId(),
    //         product.getName(),
    //         product.getCategory(),
    //         product.getPrice(),
    //         product.getStock(),
    //         product.getMinStockLevel(),
    //         supplier
    //     );
    // }
    @GetMapping("/low-stock")
public List<Product> getLowStockProducts() {
    return productRepository.findByStockLessThanEqual(5); 
    // 5 is a threshold, you can make it dynamic
}

@GetMapping("/expiring-soon")
public List<Product> getExpiringSoon(@RequestParam(defaultValue = "7") int days) {
    LocalDate today = LocalDate.now();
    LocalDate threshold = today.plusDays(days);
    return productRepository.findByExpiryDateBetween(today, threshold);
}


}
