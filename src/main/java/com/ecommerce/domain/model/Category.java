package com.ecommerce.domain.model;

import lombok.Data;

/**
 * @author ilker Kopan
 */
@Data
public class Category {
    private String title;
    private Category parentCategory;

    public Category(String title) {
        this.title = title;
    }

    public Category(String title, Category parentCategory) {
        this.title = title;
        this.parentCategory = parentCategory;
    }
}
