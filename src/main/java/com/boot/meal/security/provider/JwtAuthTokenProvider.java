package com.boot.meal.security.provider;

import com.boot.meal.common.util.SecurityUtils;
import com.boot.meal.security.exception.TokenValidFailedException;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.security.InvalidKeyException;
import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Slf4j
public class JwtAuthTokenProvider implements AuthTokenProvider<JwtAuthToken> {

    private final Key key;
    private static final String AUTHORITIES_KEY = "role";

    public JwtAuthTokenProvider(String base64Secret) throws InvalidKeyException {
        byte[] keyBytes = Base64.decodeBase64(base64Secret);
        this.key = SecurityUtils.hmacShaKeyFor(keyBytes);
    }

    @Override
    public JwtAuthToken createAuthToken(String id, String role, Date expiredDate) {
        return new JwtAuthToken(id, role, expiredDate, key);
    }

    @Override
    public JwtAuthToken convertAuthToken(String token) {
        return new JwtAuthToken(token, key);
    }

    @Override
    public Authentication getAuthentication(JwtAuthToken authToken) {
        if(authToken.validate()) {

            Claims claims = authToken.getData();
            Collection<? extends GrantedAuthority> authorities =
                    Arrays.stream(new String[]{claims.get(AUTHORITIES_KEY).toString()})
                            .map(SimpleGrantedAuthority::new)
                            .collect(Collectors.toList());

            User principal = new User(claims.getSubject(), "", authorities);
            return new UsernamePasswordAuthenticationToken(principal, authToken, authorities);

        } else {
            throw new TokenValidFailedException();
        }
    }

}
