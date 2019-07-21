package com.example.coffeeblend.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "menu")
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    private Dessert dessert;
    @ManyToOne
    private MainDish mainDish;
    @ManyToOne
    private Drink drink;
    @ManyToOne
    private Starter starter;

}
