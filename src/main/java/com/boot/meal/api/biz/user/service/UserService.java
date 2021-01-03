package com.boot.meal.api.biz.user.service;

import com.boot.meal.api.biz.user.domain.User;
import com.boot.meal.api.biz.user.domain.dto.UserRequestDTO;
import com.boot.meal.api.biz.user.domain.dto.UserResponseDTO;
import com.boot.meal.api.biz.user.repository.UserRepository;
import com.boot.meal.common.service.BaseService;
import com.boot.meal.common.util.Header;
import com.boot.meal.common.util.SecurityUtils;
import com.boot.meal.security.Role;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserService extends BaseService<UserRequestDTO, UserResponseDTO, User> {

    @Autowired
    UserRepository userRepository;

    @Override
    public Header<UserResponseDTO> create(Header<UserRequestDTO> request) {

        UserRequestDTO userRequestDTO = request.getData();

        // 1. 동일 이메일 확인
        if( userRepository.findByEmail(userRequestDTO.getEmail()).isPresent() ){
            return Header.ERROR(300, "This email is in use");
        }

        // 2. ROLE 부여
        userRequestDTO.setRole(Role.USER);

        // 3. password 암호화
        userRequestDTO.setEncodedPassword(SecurityUtils.passwordEncoding(userRequestDTO.getPassword()));

        User user;
        try{
            user = userRepository.save(User.of(userRequestDTO));
        } catch(Exception e){
            e.printStackTrace();
            return Header.ERROR(500,"user create fail", e);
        }

        log.info("Create User Complete");
        return Header.OK(UserResponseDTO.of(user), 200,"user create success");
    }

    @Override
    public Header<UserResponseDTO> read(Long id) {
        return Header.OK(UserResponseDTO.of(userRepository.findById(id).orElse(new User())), 200, "find user success");
    }

    @Override
    public Header<UserResponseDTO> update(Header<UserRequestDTO> request) {
        return null;
    }

    @Override
    public Header delete(Long id) {
        return null;
    }

    public String name(){
        return  null;
    }

}
