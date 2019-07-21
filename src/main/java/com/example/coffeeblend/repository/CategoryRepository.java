package com.example.coffeeblend.repository;

import com.example.coffeeblend.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
