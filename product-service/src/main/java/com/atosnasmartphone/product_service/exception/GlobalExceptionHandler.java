package com.atosnasmartphone.product_service.exception;

import com.atosnasmartphone.product_service.dto.response.BaseResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<BaseResponse<Object>> handleProductNotFound(ProductNotFoundException productNotFoundException){
        return new ResponseEntity<>(BaseResponse.error(productNotFoundException.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DuplicateProductException.class)
    public ResponseEntity<BaseResponse<Object>> handleDuplicateProduct(DuplicateProductException duplicateProductException){
        return new ResponseEntity<>(BaseResponse.error(duplicateProductException.getMessage()), HttpStatus.CONFLICT);
    }
}
