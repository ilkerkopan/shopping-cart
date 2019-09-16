package com.ecommerce.domain.model;

import com.ecommerce.domain.model.Category;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Product is an item with price and category.
 *
 * @author ilker Kopan
 */
@Data
public class Product {
    private String title;
    private BigDecimal price;
    private Category category;

    public Product(String title, BigDecimal price, Category category) {
        this.title = title;
        this.price = price;
        this.category = category;
    }
}
