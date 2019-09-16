package com.ecommerce.domain.service;

import com.ecommerce.domain.model.Product;
import com.ecommerce.domain.model.ShoppingCartItem;
import com.ecommerce.domain.model.discount.Discount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * ShoppingCart is a domain model class that holds items
 * This is the aggregate root
 *
 * @author ilker Kopan
 */
@Scope("prototype")
@Service
public class ShoppingCart {
    private List<ShoppingCartItem> shoppingCartItems = new ArrayList<>();
    private List<Discount> campaignDiscounts = new ArrayList<>();;
    private List<Discount> couponDiscounts = new ArrayList<>();;
    private BigDecimal totalCampaignDiscount = BigDecimal.ZERO;
    private BigDecimal totalCouponDiscount = BigDecimal.ZERO;
    private BigDecimal totalPrice = BigDecimal.ZERO;
    private ShoppingCartPrinter printer;
    private DeliveryCostCalculator deliveryCostCalculator;

    @Autowired
    public ShoppingCart(ShoppingCartPrinter printer, DeliveryCostCalculator deliveryCostCalculator) {
        this.printer = printer;
        this.deliveryCostCalculator = deliveryCostCalculator;
    }

    public List<ShoppingCartItem> getShoppingCartItems() {
        return shoppingCartItems;
    }

    public BigDecimal getTotalDiscount() {
        return totalCampaignDiscount.add(totalCouponDiscount);
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    /**
     * Applies discounts adds delivery cost
     * @return price to be paid
     */
    public BigDecimal getTotalAmount() {
        return totalPrice.subtract(getTotalDiscount()).add(getDeliveryCost());
    }

    public BigDecimal getDeliveryCost() {
        return deliveryCostCalculator.calculateFor(this);
    }

    public void addItem(Product product, int quantity) {
        if (productAlreadyInCart(product)) {
            increaseProductsCountAlreadyInCart(product, quantity);
        } else {
            shoppingCartItems.add(new ShoppingCartItem(product, quantity));
        }
        totalPrice = totalPrice.add(product.getPrice().multiply(BigDecimal.valueOf(quantity)));
        calculateDiscounts();
    }

    private void increaseProductsCountAlreadyInCart(Product product, int amount) {
        shoppingCartItems.stream().filter(i -> i.getProduct().equals(product)).forEach(i -> i.increaseQuantity(amount));
    }

    private boolean productAlreadyInCart(Product product) {
       return shoppingCartItems.stream().anyMatch(i -> i.getProduct().equals(product));
    }

    public void applyCampaignDiscount(Discount discount) {
        campaignDiscounts.add(discount);
        calculateDiscounts();
    }

    public void applyCouponDiscount(Discount discount) {
        couponDiscounts.add(discount);
        calculateDiscounts();
    }

    private void calculateDiscounts() {
        totalCampaignDiscount = campaignDiscounts.stream().map(d -> d.applyDiscount(this)).reduce(BigDecimal.ZERO, BigDecimal::add);
        totalCouponDiscount = couponDiscounts.stream().map(d -> d.applyDiscount(this)).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void print() {
        printer.printShoppingCart(shoppingCartItems, getTotalDiscount(), getDeliveryCost(), getTotalAmount());
    }
}
