package com.example.coffeeblend.controller;

import com.example.coffeeblend.model.Cart;
import com.example.coffeeblend.repository.CartRepository;
import com.example.coffeeblend.repository.CoffeeCappuccinoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.swing.text.html.Option;
import java.util.Optional;

@Controller
public class CartController {
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private CoffeeCappuccinoRepository  coffeeCappuccinoRepository;

    @GetMapping("/cart")
    public String getCart(ModelMap modelMap) {
        modelMap.addAttribute("coffees", coffeeCappuccinoRepository.findAll());

        return "cart";
    }

    @GetMapping("/checkout")
    public String getCheckOut() {

        return "checkout";
    }

    @PostMapping("/cart")
    public String addToCart(@ModelAttribute Cart cart, @RequestParam("id") int id) {
//        Optional<>
        cartRepository.save(cart);
        return "redirect:/menu";
    }


//    @GetMapping("/singleDessert")
//    public String dessert ()
}
