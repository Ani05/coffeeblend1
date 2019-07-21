package com.example.coffeeblend.controller;

import com.example.coffeeblend.model.Category;
import com.example.coffeeblend.model.Drink;
import com.example.coffeeblend.repository.CategoryRepository;
import com.example.coffeeblend.repository.DrinkRepository;
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
public class DrinkController {
    @Value("${image.upload.dir}")
    private String imagesUploadDir;
    @Autowired
    private DrinkRepository drinkRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping("/addDrink")
    public  String getDrink(ModelMap map){

        map.addAttribute("categories", categoryRepository.findAll());

        return "addDrink";

    }
    @PostMapping("/addDrink")
    public  String addDrink(@ModelAttribute Drink drink, @ModelAttribute Category category, @RequestParam("picture")MultipartFile file) throws IOException {
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        File picture= new File(imagesUploadDir+ File.separator+fileName);
        Category category1= categoryRepository.save(category);
        file.transferTo(picture);
        drink.setPicUrl(fileName);
        drink.setCategory(category1);
        drinkRepository.save(drink);
        return "redirect:/admin";
    }

}
