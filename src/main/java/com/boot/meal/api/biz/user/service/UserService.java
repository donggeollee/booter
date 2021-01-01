package com.boot.meal.api.biz.user.service;

import com.boot.meal.api.biz.user.domain.User;
import com.boot.meal.api.biz.user.domain.UserRequestDTO;
import com.boot.meal.api.biz.user.domain.UserResponseDTO;
import com.boot.meal.api.biz.user.repository.UserRepository;
import com.boot.meal.common.service.BaseService;
import com.boot.meal.common.util.Header;

public class UserService extends BaseService<UserRequestDTO, UserResponseDTO, User> {

    private UserRepository userRepository;

    UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public Header<UserResponseDTO> create(Header<UserRequestDTO> request) {
        User user = null;
        try{
            user = userRepository.save(requestToEntity(request.getData(), User.class));
        } catch(Exception e){
            return Header.ERROR("UC500","user create fail", e);
        }

        return Header.OK(entityToResponse(user, UserResponseDTO.class), "UC200","user create success");
    }

    @Override
    public Header<UserResponseDTO> read(Long id) {
        return null;
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
