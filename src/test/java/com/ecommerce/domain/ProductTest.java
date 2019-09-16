package com.ecommerce.domain;

import com.ecommerce.domain.model.Category;
import com.ecommerce.domain.model.Product;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

/**
 * @author ilker Kopan
 */
public class ProductTest {
    @Test
    public void should_create_a_product_with_values_passed_through_constructor() {
        Category fruitCategory = new Category("Fruit");
        Product sampleProduct = new Product("apple", new BigDecimal("1.2"), fruitCategory);
        assertEquals(fruitCategory, sampleProduct.getCategory());
        assertEquals("apple", sampleProduct.getTitle());
    }
}
