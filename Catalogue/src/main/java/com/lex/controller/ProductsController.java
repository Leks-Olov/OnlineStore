package com.lex.controller;

import com.lex.client.ProductsRestClient;
import com.lex.controller.payload.NewProductPayload;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;

@Controller
@RequiredArgsConstructor
@RequestMapping("Store")
public class ProductsController {

    private final ProductsRestClient productsRestClient;

    @GetMapping("/")
    public String catalogue() {
        return "redirect:/Store/catalogue";
    }

    @GetMapping("catalogue")
    public String getProductsList(Model model) {
        model.addAttribute("products", this.productsRestClient.findAllProducts());
        return "Store/catalogue";
    }

    @GetMapping("manager/create")
    public String getCreateProductPage() {
        return "Store/manager/create";
    }

    @PostMapping("manager/create")
    public String createProduct(NewProductPayload payload, Model model) {
        try {
            this.productsRestClient.createProduct(payload);
            return "redirect:/Store/manager/cabinet";
        } catch (BadRequestException exception) {
            model.addAttribute("payload", payload);
            model.addAttribute("errors", exception.getErrors());
            return "Store/manager/create";
        }
    }

    @GetMapping("manager/cabinet")
    public String getManagerCabinetPage(Model model) {
        model.addAttribute("products", this.productsRestClient.findAllProducts());
        return "Store/manager/cabinet";
    }
}
