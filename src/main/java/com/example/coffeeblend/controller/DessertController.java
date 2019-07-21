package com.example.coffeeblend.controller;

import com.example.coffeeblend.model.Category;
import com.example.coffeeblend.model.Dessert;
import com.example.coffeeblend.repository.CategoryRepository;
import com.example.coffeeblend.repository.DessertRepository;
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
public class DessertController {
    @Value("${image.upload.dir}")
    private String imagesUploadDir;
    @Autowired
    private DessertRepository dessertRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping("/addDessert")
    public String getDessert (ModelMap map){
        map.addAttribute("categories",categoryRepository.findAll());

        return "addDessert";
    }

    @PostMapping("/addDessert")
    public  String addDessert(@ModelAttribute Dessert dessert, @ModelAttribute Category category, @RequestParam ("picture")MultipartFile file) throws IOException {
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        File picture = new File(imagesUploadDir + File.separator + fileName);
        Category category1 = categoryRepository.save(category);
        file.transferTo(picture);
        dessert.setPicUrl(fileName);
        dessert.setCategory(category1);
        dessertRepository.save(dessert);
        return "redirect:/admin";
    }
}
