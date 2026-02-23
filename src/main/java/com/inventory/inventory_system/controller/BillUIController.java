package com.inventory.inventory_system.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;  
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.inventory.inventory_system.model.Bill;
import com.inventory.inventory_system.model.BillItem;
import com.inventory.inventory_system.repository.ProductRepository;
import com.inventory.inventory_system.repository.TransactionRepository;
import com.inventory.inventory_system.service.BillService;

@Controller
@RequestMapping("/bills")
public class BillUIController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private BillService billService;

    @Autowired
    private TransactionRepository transactionRepository;

    @GetMapping
    public String showBillPage(Model model) {
        model.addAttribute("products", productRepository.findAll());
        model.addAttribute("transactions", 
            transactionRepository.findAll());
        return "bills";
    }

    @PostMapping("/generate")
public String generateBill(@RequestParam List<String> productId,
                           @RequestParam List<Integer> quantity,
                           Model model) {

    try {

        List<BillItem> items = new ArrayList<>();

        for (int i = 0; i < productId.size(); i++) {
            if (quantity.get(i) > 0) {
                BillItem item = new BillItem();
                item.setProductId(productId.get(i));
                item.setQuantity(quantity.get(i));
                items.add(item);
            }
        }

        Bill bill = billService.generateBill(items);

        model.addAttribute("bill", bill);
        model.addAttribute("successMessage", "Bill generated successfully!");

    } catch (Exception e) {

        model.addAttribute("errorMessage", e.getMessage());
    }

    model.addAttribute("products", productRepository.findAll());
    model.addAttribute("transactions", 
            transactionRepository.findAll());

    return "bills";
}
}