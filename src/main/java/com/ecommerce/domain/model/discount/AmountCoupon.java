package com.ecommerce.domain.model.discount;

import com.ecommerce.domain.service.ShoppingCart;
import lombok.Builder;

import java.math.BigDecimal;

/**
 * @author ilker Kopan
 */
@Builder
public class AmountCoupon implements Discount {
    private BigDecimal minAmount;
    private BigDecimal discountAmount;

    @Override
    public BigDecimal applyDiscount(ShoppingCart shoppingCart) {
        if (shoppingCart.getTotalPrice().compareTo(minAmount) == 1)
            return discountAmount;
        else
            return BigDecimal.ZERO;

    }
}
