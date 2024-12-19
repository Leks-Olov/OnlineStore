package com.lex.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    private Integer id;
    private String title;
    private String info;
    private String img;
    private List<String> reviews;
    private List<Double> ratings;
    private Integer price;

    public int countReview() {
        return getReviews().size();
    }

    public double averRating() {
        if (ratings.isEmpty()) {
            return 0.0; // Возвращаем 0, если рейтингов нет
        }
        return ratings.stream().mapToDouble(r -> r).average().orElse(0.0); // Используем mapToDouble для работы с double
    }

}
