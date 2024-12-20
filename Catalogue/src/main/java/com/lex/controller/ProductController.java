package com.lex.controller;

import com.lex.entity.Product;
import com.lex.payload.UpdateProductPayload;
import com.lex.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.NoSuchElementException;

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

    @GetMapping("edit")
    public String getEditProductPage() {
        return "Store/manager/edit";
    }

    @PostMapping("edit")
    public String updateProduct(@ModelAttribute("product") Product product,
                                UpdateProductPayload payload
    )  {
        this.productService.updateProduct(payload.title(), payload.info(),
                payload.price(), payload.file(), product.getId());

        return "redirect:/Store/manager/cabinet";
    }

    @PostMapping("delete")
    public String deleteProduct(@ModelAttribute("product") Product product) {
        this.productService.deleteProduct(product);
        return "redirect:/Store/manager/cabinet";
    }
}
