package com.boot.meal.common.service;

import com.boot.meal.common.util.Header;

public interface CrudInterface<Req, Res>{

    Header<Res> create(Header<Req> request);

    Header<Res> read(Long id);

    Header<Res> update(Header<Req> request);

    Header delete(Long id);
}
