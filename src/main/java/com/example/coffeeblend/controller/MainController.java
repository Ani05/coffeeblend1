package com.example.coffeeblend.controller;

import com.example.coffeeblend.model.Category;
import com.example.coffeeblend.repository.*;
import com.example.coffeeblend.security.SpringUser;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@Controller
public class MainController {

    @Value("${image.upload.dir}")
    private String imagesUploadDir;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private DrinkRepository drinkRepository;
    @Autowired
    private DessertRepository dessertRepository;
    @Autowired
    private MainDishRepository mainDishRepository;
    @Autowired
    private StarterRepository starterRepository;
    @Autowired
    private CoffeeCappuccinoRepository coffeeCappuccinoRepository;

    @GetMapping("/")
    public String main(ModelMap modelMap, @AuthenticationPrincipal SpringUser springUser) {
        modelMap.addAttribute("coffees", coffeeCappuccinoRepository.findAll());
        modelMap.addAttribute("drinks", drinkRepository.findAll());
        modelMap.addAttribute("desserts", dessertRepository.findAll());
        modelMap.addAttribute("mainDish", mainDishRepository.findAll());
        modelMap.addAttribute("starters", starterRepository.findAll());

        if (springUser != null) {
            modelMap.addAttribute("user", springUser.getUser());
        }
        return "index";
    }

    @GetMapping("/getImage")
    public void getImageAsByteArray(HttpServletResponse response, @RequestParam("picUrl") String picUrl) throws IOException {
        InputStream in = new FileInputStream(imagesUploadDir + File.separator + picUrl);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        IOUtils.copy(in, response.getOutputStream());
    }

    @GetMapping("/addCategory")
    public String getCategory() {

        return "addCategory";
    }

    @PostMapping("/addCategory")
    public String addCategory(@ModelAttribute Category category) {
        categoryRepository.save(category);
        return "redirect:/admin";
    }

    @GetMapping("/menu")
    public String getMenu(ModelMap map, @AuthenticationPrincipal SpringUser springUser) {
        map.addAttribute("drinks", drinkRepository.findAll());
        map.addAttribute("desserts", dessertRepository.findAll());
        map.addAttribute("mainDish", mainDishRepository.findAll());
        map.addAttribute("starters", starterRepository.findAll());

        if (springUser != null) {
            map.addAttribute("user", springUser.getUser());
        }
        return "menu";
    }



    @GetMapping("/services")
    public String getServices(ModelMap modelMap, @AuthenticationPrincipal SpringUser springUser) {
        if (springUser != null) {
            modelMap.addAttribute("user", springUser.getUser());
        }
        return "services";
    }

    @GetMapping("/blog")
    public String getBlog(ModelMap modelMap, @AuthenticationPrincipal SpringUser springUser) {
        if (springUser != null) {
            modelMap.addAttribute("user", springUser.getUser());
        }
        return "blog";
    }

    @GetMapping("/shop")
    public String getShop(ModelMap modelMap, @AuthenticationPrincipal SpringUser springUser) {
        if (springUser != null) {
            modelMap.addAttribute("user", springUser.getUser());
        }
        return "shop";
    }

    @GetMapping("/about")
    public String getAbout(ModelMap modelMap, @AuthenticationPrincipal SpringUser springUser) {
        if (springUser != null) {
            modelMap.addAttribute("user", springUser.getUser());
        }
        modelMap.addAttribute("coffees", coffeeCappuccinoRepository.findAll());

        return "about";
    }

    @GetMapping("/contact")
    public String getContact(ModelMap modelMap, @AuthenticationPrincipal SpringUser springUser) {
        if (springUser != null) {
            modelMap.addAttribute("user", springUser.getUser());
        }
        return "contact";
    }


}
