package com.example.coffeeblend.controller;

import com.example.coffeeblend.model.Category;
import com.example.coffeeblend.model.MainDish;
import com.example.coffeeblend.repository.CategoryRepository;
import com.example.coffeeblend.repository.MainDishRepository;
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
public class MainDishController {
    @Value("${image.upload.dir}")
    private String imagesUploadDir;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private MainDishRepository mainDishRepository;



    @GetMapping("/addMainDish")
    public String getFood(ModelMap map){
        map.addAttribute("categories", categoryRepository.findAll());
        return "addMainDish";

    }
    @PostMapping("/addMainDish")
    public String addFood(@ModelAttribute MainDish mainDish, @ModelAttribute Category category, @RequestParam("picture")MultipartFile file) throws IOException {
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        File picture= new File(imagesUploadDir+File.separator+fileName);
        file.transferTo(picture);
        Category category1 = categoryRepository.save(category);
        mainDish.setPicUrl(fileName);
        mainDish.setCategory(category1);
        mainDishRepository.save(mainDish);
        return "redirect:/admin";
    }
}
