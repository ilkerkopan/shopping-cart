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
import org.springframework.context.ApplicationContext;
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
public class ShoppingCartDiscountTest {
    @Autowired
    private ApplicationContext context;

    ShoppingCart cart;
    Category fruits = new Category("Fruits");
    Category softDrinks = new Category("Soft Drinks");
    Product apple = new Product("Apple", BigDecimal.valueOf(4.5), fruits);
    Product banana = new Product("Banana", BigDecimal.valueOf(6.5), fruits);
    Product coke = new Product("Coke", BigDecimal.valueOf(3.9), softDrinks);
    Product milk = new Product("Milk", BigDecimal.valueOf(4.9), softDrinks);

    @Before
    public void initCart() {
        cart = context.getBean(ShoppingCart.class);
        cart.addItem(apple, 3);
        cart.addItem(banana, 4);
        cart.addItem(coke, 1);
        cart.addItem(milk, 3);
    }

    @Test
    public void no_discount_eligable_for_campaign() {
        Discount softDrinkCampaign = DiscountFactory.createCampaign(softDrinks, BigDecimal.ONE, 5, DiscountType.AMOUNT);
        cart.applyCampaignDiscount(softDrinkCampaign);
        assertEquals(BigDecimal.ZERO, cart.getTotalDiscount());
    }

    @Test
    public void single_discount_eligible_for_amount_campaign() {
        Discount softDrinkCampaign = DiscountFactory.createCampaign(softDrinks, BigDecimal.ONE, 3, DiscountType.AMOUNT);
        cart.applyCampaignDiscount(softDrinkCampaign);
        assertEquals(BigDecimal.ONE, cart.getTotalDiscount());
    }

    @Test
    public void two_discount_eligible_for_amount_campaign() {
        Discount softDrinkCampaign = DiscountFactory.createCampaign(softDrinks, BigDecimal.ONE, 3, DiscountType.AMOUNT);
        cart.applyCampaignDiscount(softDrinkCampaign);
        Discount fruitCampaign = DiscountFactory.createCampaign(fruits, BigDecimal.valueOf(3), 5, DiscountType.AMOUNT);
        cart.applyCampaignDiscount(fruitCampaign);
        assertEquals(BigDecimal.valueOf(4), cart.getTotalDiscount());
    }

    @Test
    public void single_discount_eligible_for_rate_campaign() {
        Discount softDrinkCampaign = DiscountFactory.createCampaign(softDrinks, BigDecimal.valueOf(0.1), 3, DiscountType.RATE);
        cart.applyCampaignDiscount(softDrinkCampaign);
        assertEquals(coke.getPrice().add(milk.getPrice().multiply(BigDecimal.valueOf(3))).multiply(BigDecimal.valueOf(0.1)), cart.getTotalDiscount());
    }

    @Test
    public void two_discount_eligible_for_rate_campaign() {
        Discount softDrinkCampaign = DiscountFactory.createCampaign(softDrinks, BigDecimal.valueOf(0.1), 3, DiscountType.RATE);
        cart.applyCampaignDiscount(softDrinkCampaign);
        Discount fruitCampaign = DiscountFactory.createCampaign(fruits, BigDecimal.valueOf(0.5), 5, DiscountType.RATE);
        cart.applyCampaignDiscount(fruitCampaign);
        BigDecimal softDrinkDiscount = coke.getPrice().add(milk.getPrice().multiply(BigDecimal.valueOf(3))).multiply(BigDecimal.valueOf(0.1));
        BigDecimal fruitDiscount = apple.getPrice().multiply(BigDecimal.valueOf(3)).add(banana.getPrice().multiply(BigDecimal.valueOf(4))).multiply(BigDecimal.valueOf(0.5));
        assertEquals(softDrinkDiscount.add(fruitDiscount), cart.getTotalDiscount());
    }

    @Test
    public void no_discount_eligable_for_amount_coupon() {
        Discount softDrinkCampaign = DiscountFactory.createCoupon(BigDecimal.valueOf(150), BigDecimal.ONE, DiscountType.AMOUNT);
        cart.applyCouponDiscount(softDrinkCampaign);
        assertEquals(BigDecimal.ZERO, cart.getTotalDiscount());
    }

    @Test
    public void no_discount_eligable_for_rate_coupon() {
        Discount softDrinkCampaign = DiscountFactory.createCoupon(BigDecimal.valueOf(150), BigDecimal.valueOf(0.1), DiscountType.RATE);
        cart.applyCouponDiscount(softDrinkCampaign);
        assertEquals(BigDecimal.ZERO, cart.getTotalDiscount());
    }

    @Test
    public void single_discount_eligible_for_amount_coupon() {
        Discount softDrinkCampaign = DiscountFactory.createCoupon(BigDecimal.valueOf(10), BigDecimal.ONE, DiscountType.AMOUNT);
        cart.applyCampaignDiscount(softDrinkCampaign);
        assertEquals(BigDecimal.ONE, cart.getTotalDiscount());
    }

    @Test
    public void single_discount_eligible_for_rate_coupon() {
        Discount softDrinkCampaign = DiscountFactory.createCoupon(BigDecimal.valueOf(10), BigDecimal.valueOf(0.1), DiscountType.RATE);
        cart.applyCampaignDiscount(softDrinkCampaign);
        assertEquals(BigDecimal.valueOf(5.81), cart.getTotalDiscount());
    }
}
