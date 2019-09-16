package com.ecommerce.domain.model.discount;

import com.ecommerce.domain.service.ShoppingCart;
import lombok.Builder;

import java.math.BigDecimal;

/**
 * @author ilker Kopan
 */
@Builder
public class RateCoupon implements Discount {
    private BigDecimal minAmount;
    private BigDecimal discountRate;

    @Override
    public BigDecimal applyDiscount(ShoppingCart shoppingCart) {
        if (shoppingCart.getTotalPrice().compareTo(minAmount) == 1)
            return shoppingCart.getTotalPrice().multiply(discountRate);
        else
            return BigDecimal.ZERO;
    }
}
