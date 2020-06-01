package com.publicis.sapient.product.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@SuppressWarnings("serial")
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ActivePromotionInDateRange extends RuntimeException {

    public ActivePromotionInDateRange(final String message) {
        super(message);
    }
}
