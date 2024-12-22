package com.lex.controller;

import com.lex.entity.Product;
import com.lex.payload.UpdateProductPayload;
import com.lex.service.ProductService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping(("Store/manager/{productId:\\d+}"))
public class ProductController {

    private final ProductService productService;

    @ModelAttribute("product")
    public Product product(@PathVariable("productId") int productId) {
        return this.productService.findProduct(productId)
                .orElseThrow(() -> new NoSuchElementException("manager.errors.product.not_found"));
    }

    @GetMapping
    public String getProductPage(@ModelAttribute(name = "product", binding = false) Product product,
                                 Model model) {
        model.addAttribute("product", product);
        model.addAttribute("reviews", product.getReviews());
        return "Store/manager/product";
    }

    @GetMapping("edit")
    public String getEditProductPage() {
        return "Store/manager/edit";
    }

    @PostMapping("edit")
    public String updateProduct(@ModelAttribute(name = "product", binding = false) Product product,
                                @Valid UpdateProductPayload payload,
                                BindingResult bindingResult, Model model
    ) {
        if (!payload.file().isEmpty() && payload.file() != null && !bindingResult.getFieldErrors().contains("file")) {
            this.productService.updateProductFile(product.getId(), payload.file());
        }
        if (bindingResult.hasErrors()) {
            model.addAttribute("payload", payload);
            model.addAttribute("errors", bindingResult.getFieldErrors()
                    .stream()
                    .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage)));
            return "Store/manager/edit";

        } else {
            this.productService.updateProduct(payload.title(), payload.info(),
                    payload.price(), product.getId());
            return "redirect:/Store/manager/cabinet";
        }
    }

    @PostMapping("delete")
    public String deleteProduct(@ModelAttribute("product") Product product) {
        this.productService.deleteProduct(product);
        return "redirect:/Store/manager/cabinet";
    }
}
