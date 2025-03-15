package com.example.mynewfishshop.controllers;

import com.example.mynewfishshop.models.ItemModel;
import com.example.mynewfishshop.repos.ItemRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class AllItemController {

    @Autowired
    private ItemRepo itemRepo;

    @GetMapping("/catalog")
    @PreAuthorize("isAuthenticated()")
    public String listItems(Model model, Authentication authentication,
                            @RequestParam(required = false) String search,
                            @RequestParam(required = false) String minPrice,
                            @RequestParam(required = false) String maxPrice,
                            @RequestParam(required = false) String category) {
        List<ItemModel> allItems = itemRepo.findAll();

        Double minPriceValue = null;
        Double maxPriceValue = null;

        try {
            if (minPrice != null && !minPrice.isEmpty()) {
                minPriceValue = Double.parseDouble(minPrice);
            }
            if (maxPrice != null && !maxPrice.isEmpty()) {
                maxPriceValue = Double.parseDouble(maxPrice);
            }
        } catch (NumberFormatException e) {
            // Логирование или обработка ошибок
        }

        // Фильтрация товаров
        Double finalMinPriceValue = minPriceValue;
        Double finalMaxPriceValue = maxPriceValue;
        List<ItemModel> filteredItems = allItems.stream()
                .filter(item -> (search == null || item.getDisc().toLowerCase().contains(search.toLowerCase())) &&
                        (finalMinPriceValue == null || Double.parseDouble(item.getPrice()) >= finalMinPriceValue) &&
                        (finalMaxPriceValue == null || Double.parseDouble(item.getPrice()) <= finalMaxPriceValue) &&
                        (category == null || category.isEmpty() || item.getCategory().equals(category)))
                .collect(Collectors.toList());

        model.addAttribute("itemsCards", filteredItems);
        model.addAttribute("isAuthenticated", authentication != null && authentication.isAuthenticated());
        return "catalog"; // Возврат к вашему шаблону
    }
}
