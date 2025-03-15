package com.example.mynewfishshop.controllers;


import com.example.mynewfishshop.models.ItemModel;
import com.example.mynewfishshop.repos.ItemRepo;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/")
public class MainController {
    @Autowired
   private ItemRepo itemRepo;

    @GetMapping
    public String getMainPage(Authentication authentication, Model model){
        int limit=4;
        List<ItemModel> list=itemRepo.findAll();
        List<ItemModel> limitedList=list.stream()
                .limit(limit)
                .collect(Collectors.toList());
        model.addAttribute("items",limitedList);
        model.addAttribute("isAuthenticated", authentication != null && authentication.isAuthenticated());
        return "index";
    }
}
