package com.lex.controller;

import com.lex.payload.NewProductPayload;
import com.lex.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping("Store")
public class ProductsController {

    private final ProductService productService;

    @Value("${upload.path}")
    private String uploadPath;

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
    public String createProduct(Model model, NewProductPayload payload,
                                @RequestParam("file")MultipartFile file
    ) throws IOException {
        model.addAttribute("payload", payload);

        if (!file.isEmpty()) {
            File uploadDir = new File(uploadPath);

            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }

            String uuidFile=UUID.randomUUID().toString();
            String resultFilename = uuidFile + "." + file.getOriginalFilename();

            file.transferTo(new File(uploadPath + "/" + resultFilename));

            this.productService.createProduct(payload.title(), payload.info(), resultFilename, payload.price());
        }

        return "redirect:/Store/catalogue";
    }
}
