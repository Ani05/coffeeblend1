package com.example.coffeeblend.controller;

import com.example.coffeeblend.model.TableBook;
import com.example.coffeeblend.model.User;
import com.example.coffeeblend.model.UserType;
import com.example.coffeeblend.repository.TableBookRepository;
import com.example.coffeeblend.repository.UserRepository;
import com.example.coffeeblend.security.SpringUser;

import com.example.coffeeblend.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Controller
public class UserController {
    @Value("${image.upload.dir}")
    private String imagesUploadDir;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TableBookRepository tableBookRepository;
    @Autowired
    private MailService mailService;

    @GetMapping("/login")
    public String loginPage() {

        return "login";
    }

    @GetMapping("/loginSuccess")
    public String loginSuccess(@AuthenticationPrincipal SpringUser springUser) {
        if (springUser.getUser().getUserType() == UserType.ADMIN && springUser.getUser().isActive()) {
            return "redirect:/admin";
        } else if (springUser.getUser().getUserType() == UserType.USER && springUser.getUser().isActive()) {
            return "redirect:/user";
        }


        return "redirect:/";
    }

    @GetMapping("/user")
    public String userPage(ModelMap modelMap, @AuthenticationPrincipal SpringUser springUser) {
        modelMap.addAttribute("users", springUser.getUser());
        return "user";
    }

    @GetMapping("/admin")
    public String adminPage(ModelMap modelMap,@AuthenticationPrincipal SpringUser springUser) {
        modelMap.addAttribute("users", springUser.getUser());
        return "admin";
    }

    @GetMapping("/register")
    public String register() {

        return "register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute User user, @RequestParam("picture") MultipartFile file) throws IOException {
        if (!userRepository.findByEmail(user.getEmail()).isPresent()) {
            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            File picture = new File(imagesUploadDir + File.separator + fileName);
            String token = UUID.randomUUID().toString();
            file.transferTo(picture);
            user.setPicUrl(fileName);
            user.setToken(token);
            user.setUserType(UserType.USER);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
            mailService.sendSimpleMessage(user.getEmail(), "Welcome dear " + user.getName(), "Please check the link for activating your profile: \n http://localhost:8080/activateProfile?token=" + token);
            return "redirect:/login";
        } else {
            return "redirect:/register";
        }
    }

    @GetMapping("/activateProfile")
    public String activate(@RequestParam("token") String token) {
        Optional<User> byToken = userRepository.findByToken(token);
        if (byToken.isPresent()) {
            byToken.get().setActive(true);
            byToken.get().setToken("");
            userRepository.save(byToken.get());

        }
        return "redirect:/login";

    }

    @PostMapping("/bookAtable")

    public String bookAtable(@ModelAttribute TableBook tableBook,
                             @RequestParam("startDate") String startDate,
                             @RequestParam("startTime") String startTime,
                             @RequestParam("name") String name,
                             @RequestParam("surname") String surname,
                             @RequestParam("phone") String phone,
                             @RequestParam("message") String message,
                             @AuthenticationPrincipal SpringUser springUser) throws ParseException {
        if (springUser != null) {
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = format.parse(startDate);
            Date time = format.parse(startTime);
            tableBook.setUser(springUser.getUser());
            tableBook.setStartDate(date);
            tableBook.setTime(time);
            tableBook.setName(name);
            tableBook.setSurname(surname);
            tableBook.setPhone(phone);
            tableBook.setMessage(message);
            tableBookRepository.save(tableBook);
            return "redirect:/bookTable";
        }
        return "redirect:/";
    }


}
