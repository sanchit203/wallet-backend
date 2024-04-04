package com.walletbackend.controller;

import com.walletbackend.dto.ErrorResponse;
import com.walletbackend.exception.AuthenticationException;
import com.walletbackend.exception.InvalidInputDataException;
import com.walletbackend.exception.UniqueKeyViolationException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalControllerAdvice {
    @Autowired
    HttpServletRequest request;

    @ExceptionHandler({AuthenticationException.class})
    public ResponseEntity<ErrorResponse> handleAuthenticationException(AuthenticationException e){
        ErrorResponse errorResponse = ErrorResponse.builder()
                .message(e.getMessage())
                .status(HttpStatus.BAD_REQUEST)
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({InvalidInputDataException.class})
    public ResponseEntity<ErrorResponse> handleInvalidInputDataException(InvalidInputDataException e){
        ErrorResponse errorResponse = ErrorResponse.builder()
                .message(e.getMessage())
                .status(HttpStatus.BAD_REQUEST)
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({UniqueKeyViolationException.class})
    public ResponseEntity<ErrorResponse> handleUniqueKeyViolationException(UniqueKeyViolationException e){
        ErrorResponse errorResponse = ErrorResponse.builder()
                .message(e.getMessage())
                .status(HttpStatus.BAD_REQUEST)
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
