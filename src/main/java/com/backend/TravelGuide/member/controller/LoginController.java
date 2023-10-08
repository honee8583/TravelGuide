package com.backend.TravelGuide.member.controller;

import com.backend.TravelGuide.member.domain.MemberRequestDTO;
import com.backend.TravelGuide.member.service.impl.LoginServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "로그인 컨트롤러")
@Slf4j
@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
public class LoginController {
    private final LoginServiceImpl loginService;

    @Operation(summary = "카카오 로그인")
    @PostMapping("/kakao")
    public ResponseEntity<String> kakaoLoginJs(@RequestBody MemberRequestDTO.KakaoLoginDTO loginDto) throws Exception {
        log.info(">> 카카오 회원 로그인");
        log.info(loginDto.toString());

        String token = loginService.kakaoLogin(loginDto);
        log.info(token);

        return ResponseEntity.ok().body(token);
    }

    @Operation(summary = "일반 로그인")
    @PostMapping("/normal")
    public ResponseEntity<String> login(@RequestBody MemberRequestDTO.MemberLoginDTO loginDTO) {
        log.info(">> 일반 회원 로그인");
        log.info(loginDTO.toString());
        String token = loginService.normalLogin(loginDTO);

        return ResponseEntity.ok().body(token);
    }
}
