package com.boot.test.api.biz.menu.domain;

import com.boot.test.common.domain.BaseEntity;
import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Entity
public class Menu extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    Long restaurantId;
    String name;
    String price;
    Boolean isSignature;
    String imageUrl;
}
