package com.boot.meal.api.controller.api;


import com.boot.meal.api.biz.user.domain.User;
import com.boot.meal.api.biz.user.domain.dto.UserRequestDTO;
import com.boot.meal.api.biz.user.domain.dto.UserResponseDTO;
import com.boot.meal.api.biz.user.service.LoginService;
import com.boot.meal.common.util.Header;
import com.boot.meal.security.exception.LoginFailedException;
import com.boot.meal.security.provider.JwtAuthToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/login")
@RestController
public class LoginController {

    private final LoginService loginService;

    @PostMapping
    public Header<UserResponseDTO> login(@RequestBody UserRequestDTO userRequest){

        Optional<User> optionalUser = loginService.login(userRequest);

        if(optionalUser.isPresent()){
            JwtAuthToken jwtAuthToken = (JwtAuthToken)loginService.createAuthToken(optionalUser.get());

            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("user", optionalUser.get());
            resultMap.put("jwtAuthToken", jwtAuthToken.getToken());

            UserResponseDTO userResponse = UserResponseDTO.of(optionalUser.get());
            userResponse.setJwtToken(jwtAuthToken.getToken());

            return Header.OK(userResponse,200,"JWT Login complete");

        } else {
            throw new LoginFailedException();
        }
    }
}
