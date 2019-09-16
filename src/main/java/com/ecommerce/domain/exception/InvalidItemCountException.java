package com.ecommerce.domain.exception;

/**
 * This exception is thrown when negative number of items tried to added to cart
 *
 * @author ilker Kopan
 */
public class InvalidItemCountException extends Exception {
    public InvalidItemCountException(String message) {
        super(message);
    }
}
