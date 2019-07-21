package com.example.coffeeblend.repository;

import com.example.coffeeblend.model.Category;
import com.example.coffeeblend.model.Drink;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DrinkRepository extends JpaRepository<Drink, Integer> {
}
