package com.example.demo.repository;

import com.example.demo.entity.CoffeeItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CoffeeRepository extends JpaRepository<CoffeeItem, Integer> {
    public List<CoffeeItem> findByDescription(String description);
    public List<CoffeeItem> findByName(String name);
    public List<CoffeeItem> findByPrice(double price);
}
