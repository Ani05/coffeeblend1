package com.example.coffeeblend.repository;

import com.example.coffeeblend.model.Category;
import com.example.coffeeblend.model.Dessert;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DessertRepository extends JpaRepository<Dessert, Integer> {
}
