package com.lex.controller;

import com.lex.controller.payload.NewProductPayload;
import com.lex.entity.Product;
import com.lex.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("manager-api/products")
public class ProductsRestController {

    private final ProductService productService;

    @GetMapping
    public List<Product> findProducts() {
        return this.productService.findAllProducts();
    }

    @PostMapping
    public ResponseEntity<?> createProduct(@ModelAttribute @Valid NewProductPayload payload,
                                           BindingResult bindingResult
    ) throws BindException {
        if (bindingResult.hasErrors()) {
            if (bindingResult instanceof BindException exception) {
                throw exception;
            } else {
                throw new BindException(bindingResult);
            }
        }
        else {
            this.productService.createProduct(payload.title(), payload.info(),
                    payload.file(), payload.price());
            return ResponseEntity.noContent()
                    .build();
        }
    }
}
