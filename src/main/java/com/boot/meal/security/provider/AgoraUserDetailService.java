package com.boot.meal.security.provider;

import com.boot.meal.api.biz.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AgoraUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        log.info("loadUserByUsername : {}", email);

        return userRepository.findByEmail(email)
                .map(this::createSpringSecurityUser)
                .orElseThrow(RuntimeException::new);
    }

    private User createSpringSecurityUser(com.boot.meal.api.biz.user.domain.User user) {
        List<GrantedAuthority> grantedAuthorities = Collections.singletonList(new SimpleGrantedAuthority(user.getRole()));
        //TODO: username 에 email을 넣는 방법이 적합한지?
        return new User(user.getEmail(), user.getPassword(), grantedAuthorities);
    }
}
