package com.backend.TravelGuide.review.error;

import com.backend.TravelGuide.member.error.exception.CustomException;
import org.springframework.http.HttpStatus;

public class ReplyNotExistsException extends CustomException {

    @Override
    public int getStatusCode() {
        return HttpStatus.BAD_REQUEST.value();
    }

    @Override
    public String getMessage() {
        return "존재하지 않는 댓글입니다!";
    }
}
