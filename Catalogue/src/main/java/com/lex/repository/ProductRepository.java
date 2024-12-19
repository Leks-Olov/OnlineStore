package com.lex.repository;

import com.lex.entity.Product;

import java.util.List;

public interface ProductRepository {
    List<Product> findAll();

    void save(Product product);
}
