package com.boot.meal.api.biz.notification.domain;

import com.boot.meal.common.domain.BaseEntity;
import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.time.LocalTime;

@Getter
@Entity
public class Notification extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotNull
    private Double movingDistance;
    @NotNull
    private LocalTime movingTime;
    private String blogUrlLink;

//    @ManyToOne
//    private Restaurant restaurant;
//    @ManyToOne
//    private User user;
//    @ManyToOne
//    private Access access;

}
