package com.ecommerce.domain;

import com.ecommerce.domain.model.Category;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author ilker Kopan
 */
public class CategoryTest {

    @Test
    public void category_without_parent() {
        Category fruitCategory = new Category("Fruit");
        assertEquals("Fruit", fruitCategory.getTitle());
    }

    @Test
    public void category_with_parent_category() {
        Category fruitAndVegetablesCategory = new Category("Food");
        Category fruitCategory = new Category("Fruit", fruitAndVegetablesCategory);

        assertEquals(fruitAndVegetablesCategory, fruitCategory.getParentCategory());
        assertEquals("Food", fruitAndVegetablesCategory.getTitle());
        assertEquals("Fruit", fruitCategory.getTitle());
    }
}
