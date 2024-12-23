package com.lex.client;

import com.lex.controller.BadRequestException;
import com.lex.controller.payload.NewProductPayload;
import com.lex.controller.payload.UpdateProductPayload;
import com.lex.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

@RequiredArgsConstructor
public class ProductsRestClientImpl implements ProductsRestClient {

    private static final ParameterizedTypeReference<List<Product>> PRODUCTS_TYPE_REFERENCE =
            new ParameterizedTypeReference<>() {};

    private final RestClient restClient;

    @Override
    public List<Product> findAllProducts() {
        return this.restClient
                .get()
                .uri("/manager-api/products")
                .retrieve()
                .body(PRODUCTS_TYPE_REFERENCE);
    }

    @Override
    public void createProduct(NewProductPayload payload) {
        try {
            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
            body.add("title", payload.title());
            body.add("info", payload.info());
            body.add("price", payload.price());
            body.add("file", payload.file().getResource());
            this.restClient
                    .post()
                    .uri("/manager-api/products")
                    .contentType(MediaType.MULTIPART_FORM_DATA)
                    .body(body)
                    .retrieve()
                    .toBodilessEntity();
        } catch (HttpClientErrorException.BadRequest exception) {
            ProblemDetail problemDetail = exception.getResponseBodyAs(ProblemDetail.class);
            throw new BadRequestException((Map<String, String>) problemDetail.getProperties().get("errors"));
        }
    }

    @Override
    public Optional<Product> findProduct(int productId) {
        try {
           return Optional.ofNullable(this.restClient.get()
                   .uri("/manager-api/products/{productId}", productId)
                   .retrieve()
                   .body(Product.class));
        } catch (HttpClientErrorException.NotFound exception) {
            return Optional.empty();
        }
    }

    @Override
    public void updateProduct(UpdateProductPayload payload, int productId) {
        try {
            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
            body.add("title", payload.title());
            body.add("info", payload.info());
            body.add("price", payload.price());
            body.add("file", payload.file().getResource());
            this.restClient
                    .patch()
                    .uri("/manager-api/products/{productId}", productId)
                    .contentType(MediaType.MULTIPART_FORM_DATA)
                    .body(body)
                    .retrieve()
                    .toBodilessEntity();
        } catch (HttpClientErrorException.BadRequest exception) {
            ProblemDetail problemDetail = exception.getResponseBodyAs((ProblemDetail.class));
            throw new BadRequestException((Map<String, String>) problemDetail.getProperties().get("errors"));
        }

    }

    @Override
    public void deleteProduct(int productId) {
        try {
            this.restClient
                    .delete()
                    .uri("/manager-api/products/{productId}", productId)
                    .retrieve()
                    .toBodilessEntity();
        } catch (HttpClientErrorException.NotFound exception) {
            throw new NoSuchElementException(exception);
        }
    }
}
