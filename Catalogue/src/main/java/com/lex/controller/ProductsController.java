package com.lex.controller;

import com.lex.payload.NewProductPayload;
import com.lex.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("Store")
public class ProductsController {

    private final ProductService productService;

    @GetMapping("/")
    public String catalogue() {
        return "redirect:/Store/catalogue";
    }

    @GetMapping("catalogue")
    public String getProductsList(Model model) {
        model.addAttribute("products", productService.findAllProducts());
        return "Store/catalogue";
    }

    @GetMapping("manager/create")
    public String getCreateProductPage() {
        return "Store/manager/create";
    }

    @PostMapping("manager/create")
    public String createProduct(Model model, NewProductPayload payload) {
        model.addAttribute("payload", payload);
        this.productService.createProduct(payload.title(), payload.info(), payload.img(), payload.price());
        return "redirect:/Store/catalogue";
    }
}
