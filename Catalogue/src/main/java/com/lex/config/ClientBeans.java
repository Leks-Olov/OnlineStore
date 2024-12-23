package com.lex.config;

import com.lex.client.ProductsRestClient;
import com.lex.client.ProductsRestClientImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class ClientBeans {

    @Bean
    public ProductsRestClientImpl productsRestClient (
            @Value("${Store.service.manager.uri:http://localhost:8081}") String mangerBaseUri
    ) {
        return new ProductsRestClientImpl(RestClient.builder()
                .baseUrl(mangerBaseUri)
                .build());
    }
}
