package com.boot.meal.api.controller.api;

import com.boot.meal.api.biz.user.domain.User;
import com.boot.meal.api.biz.user.domain.dto.UserRequestDTO;
import com.boot.meal.api.biz.user.domain.dto.UserResponseDTO;
import com.boot.meal.api.biz.user.service.UserService;
import com.boot.meal.common.util.Header;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1/user")
@RestController
public class UserApiController extends CrudController<UserRequestDTO, UserResponseDTO, User>{

    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public Header<UserResponseDTO> signup(@RequestBody Header<UserRequestDTO> userRequest){
        return userService.create(userRequest);
    }


}
