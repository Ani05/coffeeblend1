package com.example.coffeeblend.repository;

import com.example.coffeeblend.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Integer> {
}
