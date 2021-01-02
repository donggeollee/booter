package com.boot.meal.api.controller.api;

import com.boot.meal.common.service.BaseService;
import com.boot.meal.common.service.CrudInterface;
import com.boot.meal.common.util.Header;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@Component
public class CrudController<Req, Res, Entity> implements CrudInterface<Req, Res> {

    protected BaseService<Req, Res, Entity> baseService;

    @Override
    @PostMapping("")
    public Header<Res> create(@RequestBody Header<Req> request) {
        log.info("request param : {}", request);
        return baseService.create(request);
    }

    @Override
    @GetMapping("{id}")
    public Header<Res> read(@PathVariable Long id) {
        log.info("request param : {}", id);
        return baseService.read(id);
    }

    @Override
    @PutMapping("")
    public Header<Res> update(@RequestBody Header<Req> request) {
        return baseService.update(request);
    }

    @Override
    @DeleteMapping("{id}")
    public Header delete(@PathVariable Long id) {
        return baseService.delete(id);
    }
}
