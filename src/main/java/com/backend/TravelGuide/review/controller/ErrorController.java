package com.backend.TravelGuide.review.controller;

import com.backend.TravelGuide.member.error.ErrorResponse;
import com.backend.TravelGuide.member.error.exception.JwtInvalidException;
import com.backend.TravelGuide.member.error.exception.NoJwtTokenException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@Slf4j
@RequestMapping("/error")
@RestController
public class ErrorController {

    @GetMapping("/noJwt")
    public ResponseEntity<ErrorResponse> noJwtError() {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setStatusCode(HttpStatus.FORBIDDEN.value());
        errorResponse.setMessages(Arrays.asList(new NoJwtTokenException().getMessage()));

        return ResponseEntity.ok(errorResponse);
    }

    @GetMapping("/invalidJwt")
    public ResponseEntity<ErrorResponse> invalidJwtError() {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setStatusCode(HttpStatus.FORBIDDEN.value());
        errorResponse.setMessages(Arrays.asList(new JwtInvalidException().getMessage()));

        return ResponseEntity.ok(errorResponse);
    }
}
