package com.lex.repository;

import com.lex.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {
    List<Product> findAll();

    void save(Product product);

    Optional<Product> findByID(int productId);

    void delete(Product product);
}
