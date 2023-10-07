package com.backend.TravelGuide.review.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class UploadTestController {
    @GetMapping("/uploadEx")
    public void uploadEx() {
        log.info("uploadEx..");
    }
}
