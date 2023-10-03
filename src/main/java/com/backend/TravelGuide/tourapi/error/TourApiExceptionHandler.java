package com.backend.TravelGuide.tourapi.error;

import com.backend.TravelGuide.member.error.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpServerErrorException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Component
@RestControllerAdvice
public class TourApiExceptionHandler {

    @ExceptionHandler(MissingServletRequestParameterException.class)
    protected ResponseEntity<ErrorResponse> missingServletRequestParameterExceptionHandler(MethodArgumentNotValidException e) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .messages(Arrays.asList(e.getMessage()))
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpServerErrorException.class)
    protected ResponseEntity<ErrorResponse> httpserverErrorExceptionHandler(HttpServerErrorException e) {
        ErrorResponse errorResponse = ErrorResponse
                .builder()
                .statusCode(e.getStatusCode().value())
                .messages(Arrays.asList(e.getMessage()))
                .build();

        return new ResponseEntity<>(errorResponse, e.getStatusCode());
    }

    @ExceptionHandler(JSONException.class)
    protected ResponseEntity<ErrorResponse> jsonExceptionHandler(JSONException e) {
        ErrorResponse errorResponse = ErrorResponse
                .builder()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .messages(Arrays.asList(e.getMessage()))
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
