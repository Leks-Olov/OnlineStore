package com.lex.custom_annotation.imgValid;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class FileTypeValidator implements ConstraintValidator<ValidImageFile, MultipartFile> {

    private static final List<String> VALID_CONTENT_TYPES = List.of("image/jpeg", "image/png", "image/gif");

    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext context) {


        if (!VALID_CONTENT_TYPES.contains(file.getContentType()) && !file.isEmpty() && file != null) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("{manager.products.errors.file_is_not_img}")
                    .addConstraintViolation();
            return false;
        }

        return true;
    }
}

