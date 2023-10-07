package com.backend.TravelGuide.member.error;

import com.backend.TravelGuide.member.error.exception.CustomException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Component
@RestControllerAdvice
public class MemberExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ErrorResponse> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        List<String> errorMessages = new ArrayList<>();
        for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
            String errorMessage = fieldError.getDefaultMessage();
            errorMessages.add(errorMessage);
        }

        ErrorResponse errorResponse = ErrorResponse.builder()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .messages(errorMessages)
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    protected ResponseEntity<ErrorResponse> usernameNotFoundExceptionHandler(UsernameNotFoundException e) {
        ErrorResponse errorResponse = ErrorResponse
                .builder()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .messages(Arrays.asList(e.getMessage()))
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.resolve(HttpStatus.BAD_REQUEST.value()));
    }

    @ExceptionHandler(CustomException.class)
    protected ResponseEntity<ErrorResponse> customExceptionHandler(CustomException e) {
        ErrorResponse errorResponse = ErrorResponse
                .builder()
                .statusCode(e.getStatusCode())
                .messages(Arrays.asList(e.getMessage()))
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.resolve(e.getStatusCode()));
    }
}
