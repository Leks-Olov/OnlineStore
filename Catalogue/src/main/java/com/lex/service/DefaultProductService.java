package com.lex.service;

import com.lex.entity.Product;
import com.lex.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DefaultProductService implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public List<Product> findAllProducts() {
        return this.productRepository.findAll();
    }

    @Override
    public void createProduct(String title, String info, String img, int price) {
        this.productRepository.save(new Product(null, title, info, img,
                new ArrayList<>(), new ArrayList<>(), price));
    }
}
