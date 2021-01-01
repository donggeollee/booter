package com.boot.meal.api.controller.api;

import com.boot.meal.api.biz.user.domain.User;
import com.boot.meal.api.biz.user.domain.UserRequestDTO;
import com.boot.meal.api.biz.user.domain.UserResponseDTO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/user")
@RestController
public class UserApiController extends CrudController<UserRequestDTO, UserResponseDTO, User>{

}
