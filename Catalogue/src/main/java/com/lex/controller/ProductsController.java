package com.lex.controller;

import com.lex.payload.NewProductPayload;
import com.lex.service.ProductService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.util.stream.Collectors;

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
        model.addAttribute("products", this.productService.findAllProducts());
        return "Store/catalogue";
    }

    @GetMapping("manager/create")
    public String getCreateProductPage() {
        return "Store/manager/create";
    }

    @PostMapping("manager/create")
    public String createProduct(@Valid NewProductPayload payload,
                                BindingResult bindingResult, Model model
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("payload", payload);
            model.addAttribute("errors", bindingResult.getFieldErrors()
                    .stream()
                    .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage)));
            return "Store/manager/create";
        }

        this.productService.createProduct(payload.title(), payload.info(), payload.file(), payload.price());
        return "redirect:/Store/manager/cabinet";
    }

    @GetMapping("manager/cabinet")
    public String getManagerCabinetPage(Model model) {
        model.addAttribute("products", this.productService.findAllProducts());
        return "Store/manager/cabinet";
    }
}
