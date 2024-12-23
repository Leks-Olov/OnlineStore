package com.lex.controller;

import com.lex.client.ProductsRestClient;
import com.lex.entity.Product;
import com.lex.controller.payload.UpdateProductPayload;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;
import java.util.NoSuchElementException;

@Controller
@RequiredArgsConstructor
@RequestMapping(("Store/manager/{productId:\\d+}"))
public class ProductController {

    private final ProductsRestClient productsRestClient;

    private final MessageSource messageSource;

    @ModelAttribute("product")
    public Product product(@PathVariable("productId") int productId) {
        return this.productsRestClient.findProduct(productId)
                .orElseThrow(() -> new NoSuchElementException("manager.errors.product.not_found"));
    }

    @GetMapping
    public String getProduct(@ModelAttribute(name = "product", binding = false) Product product,
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
                                UpdateProductPayload payload, Model model
    ) {
        try {
            this.productsRestClient.updateProduct(payload, product.getId());
            return "redirect:/Store/manager/cabinet";
        } catch (BadRequestException exception) {
            model.addAttribute("payload", payload);
            model.addAttribute("errors", exception.getErrors());
            return "Store/manager/edit";
        }
    }

    @PostMapping("delete")
    public String deleteProduct(@ModelAttribute("product") Product product) {
        this.productsRestClient.deleteProduct(product.getId());
        return "redirect:/Store/manager/cabinet";
    }

    @ExceptionHandler(NoSuchElementException.class)
    public String handleNoSuchElementException(NoSuchElementException exception, Model model,
                                               HttpServletResponse response, Locale locale) {
        response.setStatus(HttpStatus.NOT_FOUND.value());
        model.addAttribute("error",
                this.messageSource.getMessage(exception.getMessage(), new Object[0],
                        exception.getMessage(), locale));
        return "Store/errors/404";
    }
}
