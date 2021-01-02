package com.boot.meal.api.biz.user.repository;

import com.boot.meal.api.biz.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Created by donggeol92@gmail.com on 2020-12-23.
 * Github : http://github.com/donggeollee
 */
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
}
