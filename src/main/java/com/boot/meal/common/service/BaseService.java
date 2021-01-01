package com.boot.meal.common.service;

import com.boot.meal.api.biz.user.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public abstract class BaseService<Req, Res, Entity> implements CrudInterface<Req, Res>{

    @Autowired(required = false)
    protected JpaRepository<Entity, Long> baseRepository;

    // TODO : reqeust 를 Entity 로 변환하는 작업 필요
    protected Entity requestToEntity(Req request, Class<User> entityClass){
        return (Entity)new Object();
    }

    // TODO : Entity 를 response 로 변환하는 작업 필요
    protected Res entityToResponse(Entity entity, Class<Res> responseClass){
        return (Res)new Object();
    }
}
