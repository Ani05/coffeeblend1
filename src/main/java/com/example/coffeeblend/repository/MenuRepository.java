package com.example.coffeeblend.repository;

import com.example.coffeeblend.model.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuRepository extends JpaRepository<Menu, Integer> {
}
