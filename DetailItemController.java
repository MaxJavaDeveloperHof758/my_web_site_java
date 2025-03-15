package com.example.mynewfishshop.controllers;

import com.example.mynewfishshop.models.ClientModel;
import com.example.mynewfishshop.models.ItemModel;
import com.example.mynewfishshop.repos.ClientRepo;
import com.example.mynewfishshop.repos.ItemRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/detailItem")
@PreAuthorize("isAuthenticated()")
public class DetailItemController {
    @Autowired
    ItemRepo itemRepo;

    @Autowired
    ClientRepo clientRepo;

    @GetMapping("/{id}")
    public String getDetailPage(@PathVariable(value="id") long id,
                                Model model, Authentication authentication){
        Optional<ItemModel> optional=itemRepo.findById(id);
        List<ItemModel> list=new ArrayList<>();
        optional.ifPresent(list::add);
        model.addAttribute("item",list);
        model.addAttribute("isAuthenticated", authentication != null && authentication.isAuthenticated());
        return "details";
    }
    @PostMapping("/{id}")
    public RedirectView saveData(@PathVariable(value="id") long id,
                                 @RequestParam String name,
                                 @RequestParam String contact,
                                 @RequestParam String message){
        ClientModel clientModel=new ClientModel();
        clientModel.setName(name);
        clientModel.setContact(contact);
        clientModel.setMessage(message);
        clientModel.setItem(String.valueOf(id));
        clientModel.setActual(true);
        clientRepo.save(clientModel);
        return new RedirectView("/");
    }
}
