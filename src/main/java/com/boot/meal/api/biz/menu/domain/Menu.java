package com.boot.meal.api.biz.menu.domain;

import com.boot.meal.common.domain.BaseEntity;
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
    private Long id;
    private String name;
    private String price;
    private Boolean isSignature;
    private String thumbnailUrl;

//    @ManyToOne
//    private Restaurant restaurant;
}