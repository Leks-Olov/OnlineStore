package com.lex.payload;

import com.lex.custom_annotation.imgValid.ValidImageFile;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import org.springframework.web.multipart.MultipartFile;

public record NewProductPayload(
        @NotNull(message = "{manager.products.errors.title_is_null}")
        @Size(min = 3, max = 70, message = "{manager.products.errors.title_size_is_invalid}")
        String title,

        @Size(max = 1000, message = "{manager.products.errors.info_size_is_invalid}")
        String info,

        @NotNull(message = "{manager.products.errors.price_is_null}")
        @Positive(message = "{manager.products.errors.price_not_positive_int}")
        Integer price,

        @ValidImageFile
        MultipartFile file) {
}
