package com.publicis.sapient.product.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class PromotionDoesntExists extends RuntimeException {

    public PromotionDoesntExists(final String message) {
        super(message);
    }
}
