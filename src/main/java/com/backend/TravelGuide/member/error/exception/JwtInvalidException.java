package com.backend.TravelGuide.member.error.exception;

import org.springframework.http.HttpStatus;

public class JwtInvalidException extends CustomException {
    @Override
    public int getStatusCode() {
        return HttpStatus.FORBIDDEN.value();
    }

    @Override
    public String getMessage() {
        return "JWT 토큰이 유효하지 않습니다!";
    }
}
