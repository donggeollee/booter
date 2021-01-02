package com.boot.meal.security;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum Role {

    ADMIN("ROLE_ADMIN","관리자 권한"),
    USER("ROLE_USER","사용자 권한"),
    UNKNOWN("UNKNOWN","방문자 권한");

    private String role;
    private String description;

    Role(String role, String description){
        this.role = role;
        this.description = description;
    }

    public static Role of(String role){
        return Arrays.stream(Role.values())
                .filter(r -> r.getRole().equals(role))
                .findAny()
                .orElse(UNKNOWN);
    }

}
