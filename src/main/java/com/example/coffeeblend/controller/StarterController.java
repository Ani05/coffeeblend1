package com.example.coffeeblend.controller;

import com.example.coffeeblend.model.Category;
import com.example.coffeeblend.model.Starter;
import com.example.coffeeblend.repository.CategoryRepository;
import com.example.coffeeblend.repository.StarterRepository;
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
public class StarterController {
    @Value("${image.upload.dir}")
    private String imagesUploadDir;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private StarterRepository starterRepository;


    @GetMapping("/addStarter")
    public String getStarter(ModelMap modelMap){
        modelMap.addAttribute("categories", categoryRepository.findAll());
        return "addStarter";
    }
    @PostMapping("/addStarter")
    public  String addStarter(@ModelAttribute Starter starter, @ModelAttribute Category category, @RequestParam("picture")MultipartFile file) throws IOException {
        String fileName= System.currentTimeMillis()+"_"+file.getOriginalFilename();
        File picture = new File(imagesUploadDir+File.separator+fileName);
        file.transferTo(picture);
        Category category1 =categoryRepository.save(category);
        starter.setPicUrl(fileName);
        starter.setCategory(category1);
        starterRepository.save(starter);
        return "redirect:/admin";
    }
}
