package com.ecommerce.domain.model.discount;

import com.ecommerce.domain.model.Category;
import com.ecommerce.domain.service.ShoppingCart;
import lombok.Builder;

import java.math.BigDecimal;

/**
 * @author ilker Kopan
 */
@Builder
public class AmountCampaign implements Discount {
    private Category category;
    private int minItemCount;
    private BigDecimal discountAmount;

    @Override
    public BigDecimal applyDiscount(ShoppingCart shoppingCart) {
        long itemCount = shoppingCart.getShoppingCartItems().stream().filter(i -> i.getProduct().getCategory().equals(category)).mapToInt(i -> i.getQuantity()).sum();
        if (itemCount > minItemCount)
            return discountAmount;
        else
            return BigDecimal.ZERO;
    }
}
