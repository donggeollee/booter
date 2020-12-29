package com.boot.meal.api.biz.restaurant.domain;

import com.boot.meal.api.biz.menu.domain.Menu;
import lombok.Getter;

import javax.persistence.*;
import java.util.List;

@Getter
@Entity
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;
    private String name;
    private String type;
    private String country;                                         // ENUM :: 지원할 나라 고민, 다국어처리도 고민
    private String location;
    private String city; // 도시
    private String sub1; // 구
    private String sub2; //
    private String sub3;
    private Double coordinate;  									// 경도
    private Double latitude;										// 위도

    private Double grade;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant")
    private List<Menu> menuList;


}
