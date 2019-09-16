package com.ecommerce.domain.service;

import com.ecommerce.domain.model.Category;
import com.ecommerce.domain.model.Product;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

/**
 * @author ilker Kopan
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class DeliveryCostCalculatorTest {
    private ShoppingCart cart;

    @Autowired
    private DeliveryCostCalculator deliveryCostCalculator;

    @Autowired
    private ApplicationContext context;

    @Before
    public void cleanCart() {
        cart = context.getBean(ShoppingCart.class);
    }
    @Test
    public void must_calculate_zero_for_empty_cart() {
        assertEquals(BigDecimal.ZERO, deliveryCostCalculator.calculateFor(cart));
    }

    @Test
    public void delivery_cost_for_a_single_item() {
        Category fruits = new Category("Fruits");
        Product apple = new Product("Apple", new BigDecimal("4.5"), fruits);
        cart.addItem(apple, 1);
        assertEquals(BigDecimal.TEN.add(BigDecimal.ONE.add(new BigDecimal("2.99"))), deliveryCostCalculator.calculateFor(cart));
    }

    @Test
    public void delivery_cost_for_an_item_quantity_of_two() {
        Category fruits = new Category("Fruits");
        Product apple = new Product("Apple", new BigDecimal("4.5"), fruits);
        cart.addItem(apple, 2);
        assertEquals(BigDecimal.TEN.add(BigDecimal.ONE.add(new BigDecimal("2.99"))), deliveryCostCalculator.calculateFor(cart));
    }

    @Test
    public void delivery_cost_for_two_items_quantity_of_one() {
        Category fruits = new Category("Fruits");
        Product apple = new Product("Apple", new BigDecimal("4.5"), fruits);
        Product banana = new Product("Banana", new BigDecimal("6.5"), fruits);
        cart.addItem(apple, 1);
        cart.addItem(banana, 1);
        assertEquals(BigDecimal.TEN.add(new BigDecimal(2).add(new BigDecimal("2.99"))), deliveryCostCalculator.calculateFor(cart));
    }

    @Test
    public void delivery_cost_for_two_items_two_categories_quantity_of_one() {
        Category fruits = new Category("Fruits");
        Category softDrinks = new Category("Soft Drinks");
        Product apple = new Product("Apple", new BigDecimal("4.5"), fruits);
        Product banana = new Product("Banana", new BigDecimal("6.5"), fruits);
        Product coke = new Product("Coke", new BigDecimal("3.9"), softDrinks);
        Product milk = new Product("Milk", new BigDecimal("4.9"), softDrinks);
        cart.addItem(apple, 1);
        cart.addItem(banana, 1);
        cart.addItem(coke, 1);
        cart.addItem(milk, 2);
        assertEquals(new BigDecimal(20).add(new BigDecimal(4).add(new BigDecimal("2.99"))), deliveryCostCalculator.calculateFor(cart));
    }
}
