package com.boot.meal.api.biz.menu.domain;

import com.boot.meal.api.biz.restaurant.domain.Restaurant;
import com.boot.meal.common.domain.BaseEntity;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@ToString
@Entity
public class Menu extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String price;
    private Boolean isSignature;
    private String thumbnailUrl;

    @ManyToOne
    private Restaurant restaurant;
}
