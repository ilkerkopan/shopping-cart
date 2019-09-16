package com.ecommerce.domain.service;

import com.ecommerce.domain.model.ShoppingCartItem;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Formatter;
import java.util.List;

/**
 * Console printer for shopping cart.
 * Just prints everything to the console.
 *
 * @author ilker Kopan
 */
@Component
public class ShoppingCartConsolePrinter implements ShoppingCartPrinter {

    public static final String LINE_SEPERATOR = "------------------------------------------------------";

    @Override
    public void printShoppingCart(List<ShoppingCartItem> shoppingCartItems, BigDecimal totalDiscount, BigDecimal deliveryCost, BigDecimal totalAmount) {
        StringBuilder sbuf = new StringBuilder();
        Formatter fmt = new Formatter(sbuf);
        sbuf.append("-- Shopping Cart items -------------------------------");
        sbuf.append(System.lineSeparator());
        fmt.format("|%15s| %10s| x %5s| = %12s| %n", "product name", "unit price", "qty", "total price");
        sbuf.append(LINE_SEPERATOR);
        sbuf.append(System.lineSeparator());
        for (ShoppingCartItem cartItem : shoppingCartItems) {
            fmt.format("|%15s| %10f| x %5d| = %12f| %n", cartItem.getProduct().getTitle(), cartItem.getProduct().getPrice(), cartItem.getQuantity(), cartItem.getProduct().getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity())));
        }
        sbuf.append(LINE_SEPERATOR);
        sbuf.append(System.lineSeparator());
        fmt.format("|%36s| = %12f| %n", "Total discount", totalDiscount.negate());
        fmt.format("|%36s| = %12f| %n", "Delivery cost", deliveryCost);
        sbuf.append(LINE_SEPERATOR);
        sbuf.append(System.lineSeparator());
        fmt.format("|%36s| = %12f| %n", "Total Amount", totalAmount);
        sbuf.append(LINE_SEPERATOR);
        System.out.print(sbuf.toString());
    }
}
