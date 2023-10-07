package com.backend.TravelGuide.member.controller;

import com.backend.TravelGuide.member.domain.MemberRequestDTO;
import com.backend.TravelGuide.member.domain.MemberResponseDTO;
import com.backend.TravelGuide.member.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
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

    @Operation(summary = "회원가입")
    @PostMapping("/join")
    public ResponseEntity<Boolean> join(@Valid @RequestBody MemberRequestDTO.MemberJoinDTO joinDTO) {
        log.info(joinDTO.toString());
        memberService.join(joinDTO);

        return ResponseEntity.ok(true);
    }

    @Operation(summary = "비밀번호 찾기 - 답변 일치 여부")
    @GetMapping("/checkAnswer")
    public ResponseEntity<Boolean> checkAnswer(@Valid @RequestBody MemberRequestDTO.CheckAnswerDTO checkAnswerDTO) {
        log.info(checkAnswerDTO.toString());
        boolean result = memberService.checkAnswer(checkAnswerDTO);

        return ResponseEntity.ok().body(result);    // true(알맞은 답변), false(틀린 답변)
    }

    @Operation(summary = "비밀번호 찾기 - 새 비밀번호 입력")
    @PostMapping("/newPassword")
    public ResponseEntity<Boolean> setNewPassword(@Valid @RequestBody MemberRequestDTO.NewPasswordDTO newPasswordDTO) {
        log.info(newPasswordDTO.toString());
        memberService.setNewPassword(newPasswordDTO);

        return ResponseEntity.ok(true);
    }

    @Operation(summary = "회원 정보 조회")
    @GetMapping("/info")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<MemberResponseDTO.MemberInformationDTO> info(Principal principal) {
        MemberResponseDTO.MemberInformationDTO infoDTO
                = memberService.searchInfo(principal.getName());

        return ResponseEntity.ok().body(infoDTO);
    }

    @Operation(summary = "회원 정보 수정")
    @PutMapping("/update")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Boolean> update(@Valid @RequestBody MemberRequestDTO.UpdateInfoDTO infoDTO, Principal principal) {
        infoDTO.setEmail(principal.getName());
        log.info(infoDTO.toString());
        memberService.updateInfo(infoDTO);

        return ResponseEntity.ok(true);
    }

    @Operation(summary = "비밀번호 초기화")
    @PutMapping("/password_reset")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Boolean> resetPassword(@Valid @RequestBody MemberRequestDTO.ResetPwdDTO resetPwdDTO, Principal principal) {
        resetPwdDTO.setEmail(principal.getName());
        log.info(resetPwdDTO.toString());
        memberService.resetPassword(resetPwdDTO);

        return ResponseEntity.ok(true);
    }

    @Operation(summary = "회원탈퇴")
    @DeleteMapping("/withdraw")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Boolean> withdraw(@Valid @RequestBody MemberRequestDTO.WithdrawDTO withdrawDTO, Principal principal) {
        withdrawDTO.setEmail(principal.getName());
        log.info(withdrawDTO.toString());
        memberService.withdraw(withdrawDTO);

        return ResponseEntity.ok(true);
    }

    @Operation(summary = "이메일/닉네임 중복 검사")
    @PostMapping("/duplication")
    public ResponseEntity<Boolean> isDuplicated(@Valid @RequestBody MemberRequestDTO.CheckDuplicationDTO duplicationDTO) {
        log.info(duplicationDTO.toString());
        Boolean result = memberService.isDuplicated(duplicationDTO);
        log.info(">> 중복검사결과: " + result);

        return ResponseEntity.ok().body(result);
    }
}
