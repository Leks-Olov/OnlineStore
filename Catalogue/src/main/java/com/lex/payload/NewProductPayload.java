package com.lex.payload;

import org.springframework.web.multipart.MultipartFile;

public record NewProductPayload(
        String title,
        String info,
        int price,
        MultipartFile file) {
}
