package com.atosnasmartphone.auth_service.exception;


import com.atosnasmartphone.auth_service.dto.response.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserAlreadyExistException.class)
    public ResponseEntity<BaseResponse<Object>> handleUserAlreadyExist(UserAlreadyExistException userAlreadyExistException){
        return new ResponseEntity<>(BaseResponse.error(userAlreadyExistException.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<BaseResponse<Object>> handleUnauthorized(UnauthorizedException unauthorizedException){
        return new ResponseEntity<>(BaseResponse.error(unauthorizedException.getMessage()), HttpStatus.UNAUTHORIZED);
    }

}
