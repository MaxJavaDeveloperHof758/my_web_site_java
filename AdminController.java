package com.example.mynewfishshop.controllers;

import com.example.mynewfishshop.models.ClientModel;
import com.example.mynewfishshop.models.ItemModel;
import com.example.mynewfishshop.repos.ClientRepo;
import com.example.mynewfishshop.repos.ItemRepo;
import com.example.mynewfishshop.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class AdminController {
    @Autowired
    ItemRepo itemRepo;

    @Autowired
    private ClientService clientService;

    @Autowired
    ClientRepo clientRepo;

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String getAdminPage() {
        return "admin";
    }

    @GetMapping("admin/addItem")
    @PreAuthorize("hasRole('ADMIN')")
    public String getAddPage() {
        return "add";
    }

    @PostMapping("admin/addItem")
    @PreAuthorize("hasRole('ADMIN')")
    public RedirectView saveItem(@RequestParam String title,
                                 @RequestParam String disc,
                                 @RequestParam String photoUrl,
                                 @RequestParam String category,
                                 @RequestParam String fulldisc,
                                 @RequestParam String price,
                                 @RequestParam String price_discount) {
        ItemModel itemModel = new ItemModel();
        itemModel.setUrl(photoUrl);
        itemModel.setTitle(title);
        itemModel.setDisc(disc);
        itemModel.setCategory(category);
        itemModel.setFull_disc(fulldisc);
        itemModel.setPrice_discount(price_discount);
        itemModel.setPrice(price);
        itemRepo.save(itemModel);
        return new RedirectView("/admin");
    }

    @GetMapping("admin/checkClients")
    @PreAuthorize("hasRole('ADMIN')")
    public String getClients(Model model) {
        List<ClientModel> clientList = clientService.findClientModelsByActual(true);
        model.addAttribute("clientList", clientList);
        return "clients";
    }

    @GetMapping("admin/checkClients/notactual/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public RedirectView notActual(@PathVariable(value = "id") long id) {
        Optional<ClientModel> optional = clientRepo.findById(id);
        List<ClientModel> clientModels = new ArrayList<>();
        optional.ifPresent(clientModels::add);
        ClientModel clientModel = clientModels.get(0);
        clientModel.setActual(false);
        clientRepo.save(clientModel);
        return new RedirectView("/admin/checkClients");
    }

    @GetMapping("admin/checkNotActual")
    @PreAuthorize("hasRole('ADMIN')")
    public String getNotActual(Model model) {
        List<ClientModel> clientList = clientService.findClientModelsByActual(false);
        model.addAttribute("clientList", clientList);
        return "notactual";
    }

    @GetMapping("admin/checkNotActual/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public RedirectView deleteFrom(@PathVariable(value = "id") long id) {
        clientRepo.deleteById(id);
        return new RedirectView("/admin/checkNotActual");
    }

    @GetMapping("admin/checkItems")
    @PreAuthorize("hasRole('ADMIN')")
    public String getAllItems(Model model) {
        List<ItemModel> list = itemRepo.findAll();
        model.addAttribute("allItems", list);
        return "allItems";
    }

    @GetMapping("admin/checkItems/deleteItem/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public RedirectView deleteItem(@PathVariable(value = "id") long id) {
        itemRepo.deleteById(id);
        return new RedirectView("/admin/checkItems");
    }

    @GetMapping("admin/checkItems/editItem/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String editItem(@PathVariable(value = "id") long id,
                           Model model) {
        Optional<ItemModel> itemModel=itemRepo.findById(id);
        List<ItemModel> list=new ArrayList<>();
        itemModel.ifPresent(list::add);
        model.addAttribute("model",list);
        return "edit";
    }
    @PostMapping("admin/checkItems/editItem/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public RedirectView editPostItem(@PathVariable(value = "id") long id,
                                     @RequestParam String title,
                                     @RequestParam String disc,
                                     @RequestParam String url,
                                     @RequestParam String fulldisc,
                                     @RequestParam String price,
                                     @RequestParam String price_discount){
        Optional<ItemModel> itemModel=itemRepo.findById(id);
        List<ItemModel> list=new ArrayList<>();
        itemModel.ifPresent(list::add);
        ItemModel itemModel1=list.get(0);
        itemModel1.setDisc(disc);
        itemModel1.setTitle(title);
        itemModel1.setPrice(price);
        itemModel1.setPrice_discount(price_discount);
        itemModel1.setFull_disc(fulldisc);
        if(!url.equals("")&&url!=null){
            itemModel1.setUrl(url);
        }
        itemRepo.save(itemModel1);
        return new RedirectView("/admin/checkItems");
    }
}
