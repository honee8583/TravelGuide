package com.backend.TravelGuide.tourapi.error;

import com.backend.TravelGuide.member.error.exception.CustomException;
import org.springframework.http.HttpStatus;

public class KeywordNotExistsException extends CustomException {

    @Override
    public int getStatusCode() {
        return HttpStatus.BAD_REQUEST.value();
    }

    @Override
    public String getMessage() {
        return "키워드를 입력하지 않았습니다!";
    }
}
