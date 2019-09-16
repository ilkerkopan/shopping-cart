package com.ecommerce.domain.service;

import com.ecommerce.domain.model.ShoppingCartItem;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author ilker Kopan
 */
public interface ShoppingCartPrinter {

    void printShoppingCart(List<ShoppingCartItem> shoppingCartItems, BigDecimal totalDiscount, BigDecimal deliveryCost, BigDecimal totalAmount);
}
