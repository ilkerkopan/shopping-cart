package com.ecommerce.domain.model.discount;

import com.ecommerce.domain.service.ShoppingCart;

import java.math.BigDecimal;

/**
 * Interface for generic discount. All discounts have
 *
 * @author ilker Kopan
 */
public interface Discount {

    public BigDecimal applyDiscount(ShoppingCart shoppingCart);
}
