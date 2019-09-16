package com.ecommerce.domain.service;

import com.ecommerce.domain.model.Category;
import com.ecommerce.domain.model.Product;
import com.ecommerce.domain.model.discount.Discount;
import com.ecommerce.domain.model.discount.DiscountFactory;
import com.ecommerce.domain.model.discount.DiscountType;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

/**
 * @author ilker Kopan
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class ShoppingCartConsolePrinterTest {
    @Autowired
    ShoppingCart cart;

    Category fruits = new Category("Fruits");
    Category softDrinks = new Category("Soft Drinks");
    Product apple = new Product("Apple", BigDecimal.valueOf(4.5), fruits);
    Product banana = new Product("Banana", BigDecimal.valueOf(6.5), fruits);
    Product coke = new Product("Coke", BigDecimal.valueOf(3.9), softDrinks);
    Product milk = new Product("Milk", BigDecimal.valueOf(4.9), softDrinks);

    @Before
    public void initCart() {
        cart.addItem(apple, 3);
        cart.addItem(banana, 4);
        cart.addItem(coke, 1);
        cart.addItem(milk, 3);
        cart.addItem(apple, 2);
        Discount softDrinkCampaign = DiscountFactory.createCampaign(softDrinks, BigDecimal.ONE, 3, DiscountType.AMOUNT);
        cart.applyCampaignDiscount(softDrinkCampaign);
        Discount fruitCampaign = DiscountFactory.createCampaign(fruits, BigDecimal.valueOf(3), 5, DiscountType.AMOUNT);
        cart.applyCampaignDiscount(fruitCampaign);
    }

    /**
     * Just a test to see if prints properly. No validations applied.
     */
    @Test
    public void testOutput() {
        cart.print();
    }
}
