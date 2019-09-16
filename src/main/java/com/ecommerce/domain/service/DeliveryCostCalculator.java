package com.ecommerce.domain.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * Calculates delivery costs for the given shopping cart
 * default values are stored in the config file.
 *
 * @author ilker Kopan
 */
@Service
public class DeliveryCostCalculator {
    @Value("${ecommerce.costs.costPerDelivery:5.2}")
    private BigDecimal costPerDelivery;
    @Value("${ecommerce.costs.costPerProduct:2.5}")
    private BigDecimal costPerProduct;
    @Value("${ecommerce.costs.fixedCost:2.99}")
    private BigDecimal fixedCost;

    public BigDecimal calculateFor(ShoppingCart cart) {
        long numberOfProducts = cart.getShoppingCartItems().stream().filter(i -> i.getQuantity()>0).count();
        if (numberOfProducts < 1 )
            return BigDecimal.ZERO;
        long numberOfDeliveries = cart.getShoppingCartItems().stream().map(i -> i.getProduct().getCategory()).distinct().count();
        return costPerDelivery.multiply(BigDecimal.valueOf(numberOfDeliveries)).add(costPerProduct.multiply(BigDecimal.valueOf(numberOfProducts))).add(fixedCost);
    }
}
