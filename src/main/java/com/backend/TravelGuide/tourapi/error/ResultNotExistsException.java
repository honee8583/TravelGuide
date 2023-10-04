package com.backend.TravelGuide.tourapi.error;

import com.backend.TravelGuide.member.error.exception.CustomException;
import org.springframework.http.HttpStatus;

public class ResultNotExistsException extends CustomException {

    @Override
    public int getStatusCode() {
        return HttpStatus.BAD_REQUEST.value();
    }

    @Override
    public String getMessage() {
        return "검색 결과가 없습니다!";
    }
}
