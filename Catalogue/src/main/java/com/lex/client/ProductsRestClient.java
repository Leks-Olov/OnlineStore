package com.lex.client;

import com.lex.controller.payload.NewProductPayload;
import com.lex.controller.payload.UpdateProductPayload;
import com.lex.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductsRestClient {

    List<Product> findAllProducts();

    void createProduct(NewProductPayload payload);

    Optional<Product> findProduct(int productId);

    void updateProduct(UpdateProductPayload payload, int productId);

    void deleteProduct(int productId);
}
