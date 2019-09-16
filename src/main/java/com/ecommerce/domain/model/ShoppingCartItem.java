package com.ecommerce.domain.model;

import com.ecommerce.domain.exception.InvalidItemCountException;

/**
 * @author ilker Kopan
 */
public class ShoppingCartItem {
    private Product product;
    private int quantity;

    public ShoppingCartItem(Product product, int quantity) {
        this.product = product;
        if (quantity > 0) {
            this.quantity = quantity;
        } else {
            new InvalidItemCountException("Item count must be greater then 0.");
        }
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void increaseQuantity(int amount) {
        quantity += amount;
    }
}
