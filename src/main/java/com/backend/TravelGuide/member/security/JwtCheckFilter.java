package com.backend.TravelGuide.member.security;

import com.backend.TravelGuide.member.error.ErrorResponse;
import com.backend.TravelGuide.member.error.exception.JwtInvalidException;
import com.backend.TravelGuide.member.error.exception.NoJwtTokenException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtCheckFilter extends OncePerRequestFilter {
    public static final String TOKEN_HEADER = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {
        log.info(">>> JwtCheckFilter");

        String token = resolveTokenFromRequest(request);
//        PrintWriter writer = response.getWriter();

        if (StringUtils.hasText(token) && jwtTokenProvider.validateToken(token)) {
            Authentication authentication = jwtTokenProvider.getAuthentication(token);

            log.info(String.format("[%s] -> %s", jwtTokenProvider.getUsername(token), request.getRequestURI()));

            SecurityContextHolder.getContext().setAuthentication(authentication);
        } else if (StringUtils.hasText(token) && !jwtTokenProvider.validateToken(token)) {
            JwtInvalidException e = new JwtInvalidException();
            request.getRequestDispatcher("/error/invalidJwt").forward(request, response);
            throw e;
        } else {
            NoJwtTokenException e = new NoJwtTokenException();
            request.getRequestDispatcher("/error/noJwt").forward(request, response);
            throw e;
        }

        filterChain.doFilter(request, response);
    }

    private String resolveTokenFromRequest(HttpServletRequest request) {
        String token = request.getHeader(TOKEN_HEADER);

        if (!ObjectUtils.isEmpty(token) && token.startsWith(TOKEN_PREFIX)) {
            return token.substring(TOKEN_PREFIX.length());
        }

        return null;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String[] excludePath = {"/uploadEx", "/file", "/planner/all_planner", "/favicon.ico","/user", "/login", "/member/join", "/member/checkAnswer", "/member/newPassword", "/member/duplication", "/swagger-ui", "/v3", "/api"};
        String path = request.getRequestURI();

        log.info(path);
        return Arrays.stream(excludePath).anyMatch(path::startsWith);
    }
}
