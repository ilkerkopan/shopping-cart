package com.ecommerce.domain.model.discount;

import com.ecommerce.domain.model.Category;
import com.ecommerce.domain.service.ShoppingCart;
import lombok.Builder;

import java.math.BigDecimal;

/**
 * @author ilker Kopan
 */
@Builder
public class RateCampaign implements Discount {
    private Category category;
    private int minItemCount;
    private BigDecimal discountRate;


    @Override
    public BigDecimal applyDiscount(ShoppingCart shoppingCart) {
        long itemCount = shoppingCart.getShoppingCartItems().stream().filter(i -> i.getProduct().getCategory().equals(category)).mapToInt(i -> i.getQuantity()).sum();
        if (itemCount > minItemCount) {
            BigDecimal discountableItemTotalCost = shoppingCart.getShoppingCartItems().stream().filter(i -> i.getProduct().getCategory().equals(category)).map(i -> i.getProduct().getPrice().multiply(new BigDecimal(i.getQuantity()))).reduce(BigDecimal.ZERO, BigDecimal::add);
            return discountRate.multiply(discountableItemTotalCost);
        } else
            return BigDecimal.ZERO;
    }
}
