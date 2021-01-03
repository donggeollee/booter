package com.boot.meal.api.biz.user.service;

import com.boot.meal.api.biz.user.domain.User;
import com.boot.meal.api.biz.user.domain.dto.UserRequestDTO;
import com.boot.meal.security.Role;
import com.boot.meal.security.provider.AuthToken;
import com.boot.meal.security.provider.JwtAuthTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtAuthTokenProvider jwtAuthTokenProvider;
    private final static long LOGIN_RETENTION_MINUTES = 30;

    public Optional<User> login(UserRequestDTO userRequest) {

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(userRequest.getEmail(),userRequest.getPassword());

        //사용자 비밀번호 체크, 패스워드 일치하지 않는다면 Exception 발생 및 이후 로직 실행 안됨
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        //로그인 성공하면 인증 객체 생성 및 스프링 시큐리티 설정
        SecurityContextHolder.getContext().setAuthentication(authentication);

        //TODO: 권한을 여러개 가질 수 있도록 수정 필요
        Role role = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .findFirst()
                .map(Role::of)
                .orElse(Role.UNKNOWN);

        User user = User.builder()
                        .email(userRequest.getEmail())
                        .role(role.getRole())
                        .build();

        return Optional.ofNullable(user);
    }

    public AuthToken createAuthToken(User user) {
        Date expiredDate = Date.from(LocalDateTime.now().plusMinutes(LOGIN_RETENTION_MINUTES).atZone(ZoneId.systemDefault()).toInstant());
        return jwtAuthTokenProvider.createAuthToken(user.getEmail(), user.getRole(), expiredDate);
    }
}
