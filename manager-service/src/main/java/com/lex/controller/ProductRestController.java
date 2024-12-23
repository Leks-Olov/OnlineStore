package com.lex.controller;

import com.lex.controller.payload.UpdateProductPayload;
import com.lex.entity.Product;
import com.lex.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@RequiredArgsConstructor
@RequestMapping("manager-api/products/{productId:\\d+}")
public class ProductRestController {

    private final ProductService productService;
    private final MessageSource messageSource;

    @ModelAttribute("product")
    public Product getProduct(@PathVariable("productId") int productId) {
        return this.productService.findProduct(productId)
                .orElseThrow(() -> new NoSuchElementException("manager.errors.product.not_found"));
    }

    @GetMapping
    public Product findProduct(@ModelAttribute("product") Product product) {
        return product;
    }

    @PatchMapping
    public ResponseEntity<?> updateProduct(@PathVariable("productId") int productId,
                                           @ModelAttribute @Valid UpdateProductPayload payload,
                                           BindingResult bindingResult
    ) throws BindException {

        if (!payload.file().isEmpty() && payload.file() != null && !bindingResult.getFieldErrors().contains("file")) {
            this.productService.updateProductFile(productId, payload.file());
        }

        if (bindingResult.hasErrors()) {
            if (bindingResult instanceof BindException exception) {
                throw exception;
            } else {
                throw new BindException(bindingResult);
            }
        }
        else {
            this.productService.updateProduct(payload.title(), payload.info(),
                    payload.price(), productId);
            return ResponseEntity.noContent()
                    .build();
        }
    }

    @DeleteMapping
    public ResponseEntity<?> deleteProduct(@ModelAttribute("product") Product product) {
        this.productService.deleteProduct(product);
        return ResponseEntity.noContent()
                .build();
    }
}
