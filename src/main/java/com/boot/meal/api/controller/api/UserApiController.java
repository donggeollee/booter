package com.boot.meal.api.controller.api;

import com.boot.meal.api.biz.user.domain.User;
import com.boot.meal.api.biz.user.domain.dto.UserRequestDTO;
import com.boot.meal.api.biz.user.domain.dto.UserResponseDTO;
import com.boot.meal.api.biz.user.service.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1/user")
@RestController
public class UserApiController extends CrudController<UserRequestDTO, UserResponseDTO, User>{

    UserApiController(UserService userService){
        super.baseService = userService;
    }


}
