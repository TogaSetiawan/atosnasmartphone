package com.atosnasmartphone.scheduler_service.exception;

import com.atosnasmartphone.scheduler_service.dto.BaseResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ImportInProgressException.class)
    public ResponseEntity<BaseResponse<Object>> handleInProgress(ImportInProgressException e) {
        return new ResponseEntity<>(BaseResponse.error(e.getMessage()), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ImportFailedException.class)
    public ResponseEntity<BaseResponse<Object>> handleFailed(ImportFailedException e) {
        return new ResponseEntity<>(BaseResponse.error(e.getMessage()), HttpStatus.BAD_GATEWAY);
    }
}
