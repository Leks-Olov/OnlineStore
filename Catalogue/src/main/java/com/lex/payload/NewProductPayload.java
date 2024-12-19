package com.lex.payload;


public record NewProductPayload(
        String title,
        String info,
        String img,
        int price) {
}
