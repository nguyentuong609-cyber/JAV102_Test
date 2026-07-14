package com.example.demo.controller;


import com.example.demo.entity.CoffeeItem;
import com.example.demo.repository.CoffeeRepository;
import jakarta.servlet.annotation.MultipartConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import java.util.List;

@Controller // 1. Registers this class as a Web Controller with Spring Boot
@MultipartConfig(maxFileSize = 1204*1204*50)
public class CoffeeController {

    @Autowired
    private CoffeeRepository coffeeRepository;

    @GetMapping("/shop/menu")
    public String ShowMenu(Model model) {
        List<CoffeeItem> allCofees = coffeeRepository.findAll();
        model.addAttribute("coffees", allCofees);
        return "/customer/menu";
    }

    @GetMapping("/shop/search")
    public String searchCoffee(@RequestParam("name") String name, Model model) {
        // Uses your custom repository query method to filter results
        List<CoffeeItem> filteredCoffees = coffeeRepository.findByName(name);

        model.addAttribute("menuList", filteredCoffees);

        return "customer/menu";
    }

    @PostMapping("/shop/add")
    public String addCoffeeItem(@RequestParam("name") String name,
                                @RequestParam("price") double price,
                                @RequestParam("description") String description) {

        // Create a new entity instance
        CoffeeItem newItem = new CoffeeItem();
        newItem.setName(name);
        newItem.setPrice(price);
        newItem.setDescription(description);

        // Save it directly to SQL Server using the free built-in save method
        coffeeRepository.save(newItem);

        // Redirect back to the menu URL to refresh the view and show the item
        return "redirect:/shop/menu";
    }

    @GetMapping("/shop/delete/{id}")
    public String deleteCoffeeItem(@PathVariable Integer id){
        coffeeRepository.deleteById(id);

        return "redirect:/shop/menu";
    }

    @GetMapping("/shop/edit/{id}")
    public String editCoffeeItem(@PathVariable Integer id,Model model){
        CoffeeItem coffeeItem = coffeeRepository.findById(id).orElse(null);
        model.addAttribute("coffeeItem", coffeeItem);
        return "customer/edit";
    }

    @PostMapping("/shop/edit/{id}")
    public String updateCoffee(@PathVariable Integer id,
                               @RequestParam("name") String name,
                               @RequestParam("price") double price,
                               @RequestParam("description") String description){
        CoffeeItem coffeeItem = coffeeRepository.findById(id).orElse(null);
        if(coffeeItem != null){
            coffeeItem.setName(name);
            coffeeItem.setPrice(price);
            coffeeItem.setDescription(description);

            coffeeRepository.save(coffeeItem);
        }

        return "redirect:/shop/menu";
    }
}
