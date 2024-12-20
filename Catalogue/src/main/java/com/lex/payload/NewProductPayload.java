package com.lex.payload;


public record NewProductPayload(
        String title,
        String info,
        int price) {
}
