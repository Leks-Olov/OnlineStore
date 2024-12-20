package com.lex.service;

import com.lex.entity.Product;
import com.lex.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Service
@RequiredArgsConstructor
public class DefaultProductService implements ProductService {

    private final ProductRepository productRepository;

    @Value("${upload.path}")
    private String uploadPath;

    @Override
    public List<Product> findAllProducts() {
        return this.productRepository.findAll();
    }

    @Override
    public void createProduct(String title, String info, MultipartFile file, int price) {
        String filePath = createImage(file);
        this.productRepository.save(new Product(null, title, info, filePath,
                new ArrayList<>(), new ArrayList<>(), price));
    }

    @Override
    public String createImage(MultipartFile file) {
        if (!file.isEmpty()) {
            try {
                File uploadDir = new File(uploadPath);
                if (!uploadDir.exists()) {
                    uploadDir.mkdirs();
                }

                String uuidFile = UUID.randomUUID().toString();
                String resultFilename = uuidFile + "." + file.getOriginalFilename();
                file.transferTo(new File(uploadPath + "/" + resultFilename));
                return resultFilename;

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public Optional<Product> findProduct(int productId) {
        return this.productRepository.findByID(productId);
    }

    @Override
    public void updateProduct(String title, String info, int price, MultipartFile file, Integer id) {
        this.productRepository.findByID(id)
                .ifPresentOrElse(product -> {
                    imageDelete(product.getImg());
                    product.setImg(createImage(file));
                    product.setTitle(title);
                    product.setInfo(info);
                    product.setPrice(price);
                }, () -> {
                    throw new NoSuchElementException();
                });
    }

    @Override
    public void imageDelete(String imgPath) {
        File file = new File(uploadPath + "/" + imgPath);
        file.delete();
    }

    @Override
    public void deleteProduct(Product product) {
        imageDelete(product.getImg());
        this.productRepository.delete(product);
    }


}
