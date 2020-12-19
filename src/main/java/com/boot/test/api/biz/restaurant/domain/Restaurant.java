package com.boot.test.api.biz.restaurant.domain;

import lombok.Getter;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Entity
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    String id;
    String name;
    String type;
    String country;                                         // ENUM :: 지원할 나라 고민, 다국어처리도 고민
    String location;
    String city; // 도시
    String sub1; // 구
    String sub2; //
    String sub3;
    Double coordinate;										// 경도
    Double latitude;										// 위도
//    List<Menu> MenuList;
    Double grade;
    String movingTime; // -> Recommend class 로 이동



}
