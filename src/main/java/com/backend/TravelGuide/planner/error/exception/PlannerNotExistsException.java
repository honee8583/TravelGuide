package com.backend.TravelGuide.planner.error.exception;

import com.backend.TravelGuide.member.error.exception.CustomException;
import org.springframework.http.HttpStatus;

public class PlannerNotExistsException extends CustomException {
    @Override
    public int getStatusCode() {
        return HttpStatus.NOT_FOUND.value();
    }

    @Override
    public String getMessage() {
        return "존재하지 않는 플래너입니다.";
    }
}
