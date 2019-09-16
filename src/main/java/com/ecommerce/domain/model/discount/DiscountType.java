package com.ecommerce.domain.model.discount;

/**
 * Discount can be rate or amount.
 * Rate discount applies to the price of items.
 * Amount discount a fixed discount price.
 *
 * @author ilker Kopan
 */
public enum DiscountType {
    RATE,
    AMOUNT;
}
