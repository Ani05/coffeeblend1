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
@Table(name = "coffee_cappuccino")
public class CoffeeCappuccino {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private String title;
    @Column
    private String description;
    @Column
    private double price;
    @Column(name = "pic_url")
    private String picUrl;
    @ManyToOne
    private Category category;
}
