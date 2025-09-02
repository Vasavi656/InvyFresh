package com.inventory.inventory_system.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.inventory.inventory_system.model.Order;
import com.inventory.inventory_system.model.OrderWithProductDTO;
import com.inventory.inventory_system.model.Product;
import com.inventory.inventory_system.repository.OrderRepository;
import com.inventory.inventory_system.repository.ProductRepository;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    // ✅ Create Order API
    @PostMapping
    public String placeOrder(@RequestBody Order order) {
        double total = 0;

        // ✅ Fetch each product and calculate total, also reduce stock
        for (String productId : order.getProductIds()) {
            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new RuntimeException("Product not found: " + productId));

            // Check stock
            if (product.getStock() <= 0) {
                throw new RuntimeException("Product out of stock: " + product.getName());
            }

            // total bill
            total += product.getPrice();

            // Reduce stock by 1
            product.setStock(product.getStock() - 1);
            productRepository.save(product);
        }

        order.setTotalAmount(total);
        order.setOrderDate(LocalDateTime.now());

        orderRepository.save(order);
        return "Order placed successfully!";
    }

    // ✅ Get All Orders
    @GetMapping
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @GetMapping("/{id}/with-products")
    public OrderWithProductDTO getOrderWithProducts(@PathVariable String id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        List<Product> products = new ArrayList<>();
        for (String productId : order.getProductIds()) {
            productRepository.findById(productId).ifPresent(products::add);
        }

        return new OrderWithProductDTO(
                order.getId(),
                order.getCustomerName(),
                order.getCustomerEmail(),
                products,
                order.getTotalAmount(),
                order.getOrderDate()
        );
    }
}
