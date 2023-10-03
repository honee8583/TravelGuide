package com.backend.TravelGuide.member.controller;

import com.backend.TravelGuide.member.domain.MemberRequestDTO;
import com.backend.TravelGuide.member.domain.MemberResponseDTO;
import com.backend.TravelGuide.member.service.MemberService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@Tag(name = "회원 컨트롤러")
@Slf4j
@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    // 회원가입
    @PostMapping("/join")
    public ResponseEntity<Void> join(@Valid @RequestBody MemberRequestDTO.MemberJoinDTO joinDTO) {
        log.info(joinDTO.toString());
        memberService.join(joinDTO);

        return ResponseEntity.ok().build();
    }

    // jwt(x)
    // 비밀번호 찾기 질문의 답변 매칭
    @GetMapping("/checkAnswer")
    public ResponseEntity<Boolean> checkAnswer(@Valid @RequestBody MemberRequestDTO.CheckAnswerDTO checkAnswerDTO) {
        log.info(checkAnswerDTO.toString());
        boolean result = memberService.checkAnswer(checkAnswerDTO);

        return ResponseEntity.ok().body(result);    // true(알맞은 답변), false(틀린 답변)
    }

    // jwt(x)
    // 비밀번호 찾기 -> 새 비밀번호 입력
    @PostMapping("/newPassword")
    public ResponseEntity<Void> setNewPassword(@Valid @RequestBody MemberRequestDTO.NewPasswordDTO newPasswordDTO) {
        log.info(newPasswordDTO.toString());
        memberService.setNewPassword(newPasswordDTO);

        return ResponseEntity.ok().build();
    }

    // 사용자 정보 조회
    @GetMapping("/info")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<MemberResponseDTO.MemberInformationDTO> info(Principal principal) {
        MemberResponseDTO.MemberInformationDTO infoDTO
                = memberService.searchInfo(principal.getName());

        return ResponseEntity.ok().body(infoDTO);
    }

    // 사용자 정보 업데이트
    @PutMapping("/update")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Void> update(@Valid @RequestBody MemberRequestDTO.UpdateInfoDTO infoDTO, Principal principal) {
        infoDTO.setEmail(principal.getName());
        log.info(infoDTO.toString());
        memberService.updateInfo(infoDTO);

        return ResponseEntity.ok().build();
    }

    // 비밀번호 초기화
    @PutMapping("/password_reset")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Void> resetPassword(@Valid @RequestBody MemberRequestDTO.ResetPwdDTO resetPwdDTO, Principal principal) {
        resetPwdDTO.setEmail(principal.getName());
        log.info(resetPwdDTO.toString());
        memberService.resetPassword(resetPwdDTO);

        return ResponseEntity.ok().build();
    }

    // 회원탈퇴
    @DeleteMapping("/withdraw")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Void> withdraw(@Valid @RequestBody MemberRequestDTO.WithdrawDTO withdrawDTO, Principal principal) {
        withdrawDTO.setEmail(principal.getName());
        log.info(withdrawDTO.toString());
        memberService.withdraw(withdrawDTO);

        return ResponseEntity.ok().build();
    }

    // 이메일/닉네임 중복검사
    @PostMapping("/duplication")
    public ResponseEntity<Boolean> isDuplicated(@Valid @RequestBody MemberRequestDTO.CheckDuplicationDTO duplicationDTO) {
        log.info(duplicationDTO.toString());
        Boolean result = memberService.isDuplicated(duplicationDTO);
        log.info(">> 중복검사결과: " + result);

        return ResponseEntity.ok().body(result);
    }
}
