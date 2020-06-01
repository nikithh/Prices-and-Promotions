/*
 * Copyright 2020 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.publicis.sapient.product.exception;

import java.util.Date;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 *
 * @author prejayak
 * @see Customized Response Exception
 * @since April 2020
 *
 */
@ControllerAdvice
@RestController
public class CustomizedResponseEntityException extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllExceptions(final Exception ex,
            final WebRequest request) throws Exception {
        ex.printStackTrace();
        final ExceptionResponse exceptionResponse =
                new ExceptionResponse(new Date(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<Object>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(InvalidFields.class)
    public final ResponseEntity<Object> handleInvalidFieldsException(final Exception ex,
            final WebRequest request) throws Exception {
        ex.printStackTrace();
        final ExceptionResponse exceptionResponse =
                new ExceptionResponse(new Date(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ProductAlreadyAssociatedToZone.class)
    public final ResponseEntity<Object> handleProductAlreadyAssociatedToZoneException(final Exception ex,
            final WebRequest request) throws Exception {
        ex.printStackTrace();
        final ExceptionResponse exceptionResponse =
                new ExceptionResponse(new Date(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ProductAlreadyAssociatedToCluster.class)
    public final ResponseEntity<Object> handleProductAlreadyAssociatedToClusterException(final Exception ex,
            final WebRequest request) throws Exception {
        ex.printStackTrace();
        final ExceptionResponse exceptionResponse =
                new ExceptionResponse(new Date(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ProductAlreadyExists.class)
    public final ResponseEntity<Object> handleProductAlreadyExistsException(final Exception ex,
            final WebRequest request) throws Exception {
        ex.printStackTrace();
        final ExceptionResponse exceptionResponse =
                new ExceptionResponse(new Date(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ProductDoesntExist.class)
    public final ResponseEntity<Object> handleProductDoesntExistException(final Exception ex,
            final WebRequest request) throws Exception {
        ex.printStackTrace();
        final ExceptionResponse exceptionResponse =
                new ExceptionResponse(new Date(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ProductPriceBelowMinimumSellingPrice.class)
    public final ResponseEntity<Object> handleProductPriceBelowMinimumSellingPriceException(final Exception ex,
            final WebRequest request) throws Exception {
        ex.printStackTrace();
        final ExceptionResponse exceptionResponse =
                new ExceptionResponse(new Date(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PromotionCantBeApplied.class)
    public final ResponseEntity<Object> handlePromotionCantBeAppliedException(final Exception ex,
            final WebRequest request) throws Exception {
        ex.printStackTrace();
        final ExceptionResponse exceptionResponse =
                new ExceptionResponse(new Date(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(QuantityInsufficient.class)
    public final ResponseEntity<Object> handleQuantityInsufficientException(final Exception ex,
            final WebRequest request) throws Exception {
        ex.printStackTrace();
        final ExceptionResponse exceptionResponse =
                new ExceptionResponse(new Date(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(WithdrawDateAfterAppliedDate.class)
    public final ResponseEntity<Object> handleWithdrawDateAfterAppliedDateException(final Exception ex,
            final WebRequest request) throws Exception {
        ex.printStackTrace();
        final ExceptionResponse exceptionResponse =
                new ExceptionResponse(new Date(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ProductIsAlcohol.class)
    public final ResponseEntity<Object> handleProductIsAlcoholException(final Exception ex,
            final WebRequest request) throws Exception {
        ex.printStackTrace();
        final ExceptionResponse exceptionResponse =
                new ExceptionResponse(new Date(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ProductPriceIsAlreadyFixed.class)
    public final ResponseEntity<Object> handleProductPriceIsAlreadyFixedException(final Exception ex,
            final WebRequest request) throws Exception {
        ex.printStackTrace();
        final ExceptionResponse exceptionResponse =
                new ExceptionResponse(new Date(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ProductPriceIsAlreadyCancelled.class)
    public final ResponseEntity<Object> handleProductPriceIsAlreadyCancelledException(final Exception ex,
            final WebRequest request) throws Exception {
        ex.printStackTrace();
        final ExceptionResponse exceptionResponse =
                new ExceptionResponse(new Date(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException ex,
            final HttpHeaders headers,
            final HttpStatus status,
            final WebRequest request) {
        ex.printStackTrace();
        final ExceptionResponse exceptionResponse =
                new ExceptionResponse(new Date(), "Validation Failed", ex.getBindingResult().toString());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ActivePromotionInDateRange.class)
    public final ResponseEntity<Object> handleActivePromotionInDateRangeException(final Exception ex,
            final WebRequest request) throws Exception {
        ex.printStackTrace();
        final ExceptionResponse exceptionResponse =
                new ExceptionResponse(new Date(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PromotionDoesntExists.class)
    public final ResponseEntity<Object> handlePromotionDoesntExistsException(final Exception ex,
            final WebRequest request) throws Exception {
        ex.printStackTrace();
        final ExceptionResponse exceptionResponse =
                new ExceptionResponse(new Date(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EffectivePriceAlreadyDefined.class)
    public final ResponseEntity<Object> handleEffectivePriceAlreadyDefinedException(final Exception ex,
            final WebRequest request) throws Exception {
        ex.printStackTrace();
        final ExceptionResponse exceptionResponse =
                new ExceptionResponse(new Date(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PriceAtZoneLevelNotDefined.class)
    public final ResponseEntity<Object> handlePriceAtZoneLevelNotDefinedException(final Exception ex,
            final WebRequest request) throws Exception {
        ex.printStackTrace();
        final ExceptionResponse exceptionResponse =
                new ExceptionResponse(new Date(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PriceAtClusterLevelNotDefined.class)
    public final ResponseEntity<Object> handlePriceAtClusterLevelNotDefinedException(final Exception ex,
            final WebRequest request) throws Exception {
        ex.printStackTrace();
        final ExceptionResponse exceptionResponse =
                new ExceptionResponse(new Date(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ProductImagePathDoesntExist.class)
    public final ResponseEntity<Object> handleProductImagePathDoesntExistException(final Exception ex,
            final WebRequest request) throws Exception {
        ex.printStackTrace();
        final ExceptionResponse exceptionResponse =
                new ExceptionResponse(new Date(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PageNumberOutofBounds.class)
    public final ResponseEntity<Object> handlePageNumberOutOfBounds(final Exception ex,
            final WebRequest request) throws Exception {
        ex.printStackTrace();
        final ExceptionResponse exceptionResponse =
                new ExceptionResponse(new Date(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }
}
