package com.lex.service;

import com.lex.entity.Product;

import java.util.List;

public interface ProductService {
    List<Product> findAllProducts();

    void createProduct(String title, String info, String img, int price);
}
