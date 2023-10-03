package com.backend.TravelGuide.member.service.impl;

import com.backend.TravelGuide.member.domain.Member;
import com.backend.TravelGuide.member.domain.MemberRequestDTO;
import com.backend.TravelGuide.member.domain.MemberResponseDTO;
import com.backend.TravelGuide.member.domain.Role;
import com.backend.TravelGuide.member.error.exception.PasswordNotMatchException;
import com.backend.TravelGuide.member.error.exception.UserAlreadyExistsException;
import com.backend.TravelGuide.member.repository.MemberRepository;
import com.backend.TravelGuide.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void join(MemberRequestDTO.MemberJoinDTO joinDTO) {
        if (memberRepository.existsByEmail(joinDTO.getEmail())) {
            throw new UserAlreadyExistsException();
        }

        int idx = 1;
        HashMap<Integer, String> map = new HashMap<>();
        for (String answer : joinDTO.getAnswers()) {
            map.put(idx++, answer);
        }

        Member member = Member.builder()
                .email(joinDTO.getEmail())
                .password(passwordEncoder.encode(joinDTO.getPassword()))
                .name(joinDTO.getName())
                .nickname(joinDTO.getNickname())
                .role(Role.USER)
                .fromSocial(false)
                .answers(map)
                .build();

        Member savedMember = memberRepository.save(member);

        log.info("회원가입에 성공했습니다 -> " + savedMember);
    }

    @Override
    @Transactional(readOnly = true)
    public MemberResponseDTO.MemberInformationDTO searchInfo(String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 회원입니다!"));

        return MemberResponseDTO.MemberInformationDTO.builder()
                .email(member.getEmail())
                .name(member.getName())
                .nickname(member.getNickname())
                .build();
    }

    @Override
    @Transactional
    public void updateInfo(MemberRequestDTO.UpdateInfoDTO updateInfoDTO) {
        Member member = memberRepository.findByEmail(updateInfoDTO.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 회원입니다!"));

        member.updateInfo(updateInfoDTO);

        Member updateMember = memberRepository.save(member);
        log.info("회원정보 수정에 성공하였습니다! -> " + updateMember);
    }

    @Override
    @Transactional
    public void resetPassword(MemberRequestDTO.ResetPwdDTO resetPwdDTO) {
        Member member = memberRepository.findByEmail(resetPwdDTO.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 회원입니다!"));

        if (!passwordEncoder.matches(resetPwdDTO.getOldPassword(), member.getPassword())) {
            throw new PasswordNotMatchException();
        }

        String newPassword = passwordEncoder.encode(resetPwdDTO.getNewPassword());

        member.resetPwd(newPassword);

        memberRepository.save(member);
        log.info("비밀번호 초기화에 성공하였습니다!");
    }

    @Override
    @Transactional
    public Boolean checkAnswer(MemberRequestDTO.CheckAnswerDTO checkAnswerDTO) {
        Member member = memberRepository.findByEmailAndName(checkAnswerDTO.getEmail(), checkAnswerDTO.getName())
                .orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 회원입니다!"));

        Map<Integer, String> answers = member.getAnswers();
        String answer = answers.get(checkAnswerDTO.getQuestionId());

        return answer.equals(checkAnswerDTO.getAnswer());
    }

    @Override
    @Transactional
    public void setNewPassword(MemberRequestDTO.NewPasswordDTO newPasswordDTO) {
        Member member = memberRepository.findByEmail(newPasswordDTO.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 회원입니다!"));

        String encPassword = passwordEncoder.encode(newPasswordDTO.getNewPassword());

        member.resetPwd(encPassword);

        memberRepository.save(member);

        log.info("비밀번호를 새로 생성하였습니다!");
    }

    @Override
    @Transactional
    public void withdraw(MemberRequestDTO.WithdrawDTO withdrawDTO) {
        Member member = memberRepository.findByEmail(withdrawDTO.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 회원입니다!"));

        if (!passwordEncoder.matches(withdrawDTO.getPassword(), member.getPassword())) {
            throw new PasswordNotMatchException();
        }

        memberRepository.delete(member);
        log.info(member.getEmail() + " 님의 회원탈퇴에 성공하였습니다!");
    }

    @Override
    @Transactional
    public boolean isDuplicated(MemberRequestDTO.CheckDuplicationDTO duplicationDTO) {
        // 존재하지 않는 닉네임일 경우 false 반환
        if (duplicationDTO.getNickName() != null && !duplicationDTO.getNickName().equals("")) {
            return !memberRepository.existsByNickname(duplicationDTO.getNickName());
        }

        // 존재하지 않는 이메일일 경우 false 반환
        if (duplicationDTO.getEmail() != null && !duplicationDTO.getEmail().equals("")) {
            return !memberRepository.existsByEmail(duplicationDTO.getEmail());
        }

        return true;    // true : 중복검사 통과
    }
}
