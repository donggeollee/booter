package com.boot.meal.api.biz.notification.domain;

import com.boot.meal.api.biz.access.domain.Access;
import com.boot.meal.api.biz.restaurant.domain.Restaurant;
import com.boot.meal.api.biz.user.domain.User;
import com.boot.meal.common.domain.BaseEntity;
import lombok.Getter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalTime;

@Getter
@Entity
public class Notification extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotBlank
    private Double movingDistance;
    @NotBlank
    private LocalTime movingTime;
    private String blogUrlLink;

    @ManyToOne
    private Restaurant restaurant;
    @ManyToOne
    private User user;
    @ManyToOne
    private Access access;

}
