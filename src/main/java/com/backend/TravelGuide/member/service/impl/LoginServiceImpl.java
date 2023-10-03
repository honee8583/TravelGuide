package com.backend.TravelGuide.member.service.impl;

import com.backend.TravelGuide.member.domain.Member;
import com.backend.TravelGuide.member.domain.MemberRequestDTO;
import com.backend.TravelGuide.member.domain.Role;
import com.backend.TravelGuide.member.error.exception.PasswordNotMatchException;
import com.backend.TravelGuide.member.repository.MemberRepository;
import com.backend.TravelGuide.member.security.JwtTokenProvider;
import com.backend.TravelGuide.member.service.LoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider tokenProvider;
    private final AuthenticationManager authenticationManager;

    private static final long TOKEN_EXPIRE_TIME = 1000 * 60 * 60;
    private static final String TOKEN_PREFIX = "Bearer ";

    public Member saveKakaoMemberIfNotExists(MemberRequestDTO.KakaoLoginDTO loginDto) {
        Optional<Member> optionalMember = memberRepository.findByEmailAndFromSocial(loginDto.getEmail(), true);

        // 처음 로그인하는 경우
        if (optionalMember.isEmpty()) {
            log.info(">> 처음 로그인하는 카카오 회원입니다!");
            Member member = Member.builder()
                    .email(loginDto.getEmail())
                    .name(loginDto.getNickname())
                    .role(Role.USER)
                    .password(passwordEncoder.encode("1111"))
                    .fromSocial(true)
                    .build();

            memberRepository.save(member);

            return member;
        }

        return optionalMember.get();
    }

    public String kakaoLogin(MemberRequestDTO.KakaoLoginDTO loginDto) throws Exception {
        Member member = saveKakaoMemberIfNotExists(loginDto);

        List<SimpleGrantedAuthority> roles =
                Arrays.asList(new SimpleGrantedAuthority("ROLE_" + member.getRole()));

        String jwt = TOKEN_PREFIX + tokenProvider.generateToken(member.getEmail(), roles, TOKEN_EXPIRE_TIME);

        log.info(">> 소셜 로그인 JWT 토큰: " + jwt);

        authenticate(member.getEmail(), "11111111");

        return jwt;
    }

    public String normalLogin(MemberRequestDTO.MemberLoginDTO loginDTO) {
        Optional<Member> optionalMember = memberRepository.findByEmailAndFromSocial(loginDTO.getEmail(), false);

        if (!optionalMember.isPresent()) {
            throw new UsernameNotFoundException("존재하지 않는 회원입니다!");
        }

        Member member = optionalMember.get();

        if (!passwordEncoder.matches(loginDTO.getPassword(), member.getPassword())) {
            throw new PasswordNotMatchException();
        }

        List<SimpleGrantedAuthority> roles =
                Arrays.asList(new SimpleGrantedAuthority("ROLE_" + member.getRole()));

        String jwt = TOKEN_PREFIX + tokenProvider.generateToken(loginDTO.getEmail(), roles, TOKEN_EXPIRE_TIME);

        log.info(">> 일반 로그인 JWT 토큰: " + jwt);

        return jwt;
    }

    public void authenticate(String username, String pwd) throws Exception {
        try {
            log.info(">> 강제 로그인 처리 ");

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, pwd));

            SecurityContextHolder.getContext().setAuthentication(authentication);

            log.info("로그인 성공!");
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}
