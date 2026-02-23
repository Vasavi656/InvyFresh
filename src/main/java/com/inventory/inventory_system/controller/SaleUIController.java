package com.inventory.inventory_system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.inventory.inventory_system.model.Sale;
import com.inventory.inventory_system.repository.ProductRepository;
import com.inventory.inventory_system.repository.TransactionRepository;
import com.inventory.inventory_system.service.SaleService;

@Controller
@RequestMapping("/sales")
public class SaleUIController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private SaleService saleService;

    @GetMapping
    public String showSalesPage(Model model) {
        model.addAttribute("products", productRepository.findAll());
        model.addAttribute("transactions", transactionRepository.findAll());
        return "sales";
    }

    @PostMapping("/add")
    public String addSale(@RequestParam String productId,
                          @RequestParam int quantity,
                          Model model) {

        try {

            Sale sale = new Sale();
            sale.setProductId(productId);
            sale.setQuantitySold(quantity);

            String message = saleService.processSale(sale);

            model.addAttribute("successMessage", message);

        } catch (Exception e) {

            model.addAttribute("errorMessage", e.getMessage());
        }

        model.addAttribute("products", productRepository.findAll());
        model.addAttribute("transactions", transactionRepository.findAll());

        return "sales";
    }
    
}
