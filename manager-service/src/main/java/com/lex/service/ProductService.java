package com.lex.service;

import com.lex.entity.Product;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface ProductService {
    List<Product> findAllProducts();

    void createProduct(String title, String info, MultipartFile file, int price);

    String createImage(MultipartFile file) throws IOException;

    Optional<Product> findProduct(int productId);

    void updateProduct(String title, String info, int price, Integer id);

    File pathToFile(String imgPath);

    void deleteProduct(Product product);

    void updateProductFile(Integer id, MultipartFile file);
}
