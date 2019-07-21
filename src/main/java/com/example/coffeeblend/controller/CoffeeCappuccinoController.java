package com.example.coffeeblend.controller;

import com.example.coffeeblend.model.Category;
import com.example.coffeeblend.model.CoffeeCappuccino;
import com.example.coffeeblend.repository.CategoryRepository;
import com.example.coffeeblend.repository.CoffeeCappuccinoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Controller
public class CoffeeCappuccinoController {
    @Value("${image.upload.dir}")
    private String imagesUploadDir;
    @Autowired
    private CoffeeCappuccinoRepository coffeeCappuccinoRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping("/addCoffeeCappuccino")
    public String getCap (ModelMap map){
        map.addAttribute("categories",categoryRepository.findAll());

        return "addCoffeeCappuccino";
    }

    @PostMapping("/addCoffeeCappuccino")
    public  String addCap(@ModelAttribute CoffeeCappuccino coffee, @ModelAttribute Category category, @RequestParam ("picture")MultipartFile file) throws IOException {
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        File picture = new File(imagesUploadDir + File.separator + fileName);
        file.transferTo(picture);
        Category category1 = categoryRepository.save(category);
        coffee.setPicUrl(fileName);
        coffee.setCategory(category1);
        coffeeCappuccinoRepository.save(coffee);
        return "redirect:/admin";
    }
}
