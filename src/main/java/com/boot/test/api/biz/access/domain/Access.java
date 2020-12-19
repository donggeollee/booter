package com.boot.test.api.biz.access.domain;

import com.boot.test.common.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

/**
 * @ 사용자의 접속에 대한 정보 엔티티
 */
@Getter
@Entity
public class Access extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Long id;
	Long memberId; 											// 사용자 ID
	String location; 										// 접속 장소 :: 푸시 첫 터치 시 위치
	Double coordinate;										// 경도
	Double latitude;										// 위도
	int pushTouchCount; 									// 알림 터치 횟수
	String mealTiming;											// 아침? 점심? 저녁?
	boolean isSuccess;										// 실제로 추천한 음식점에서 먹었는지 :: 마지막 추천 장소에 대해 모니터링 하며 15분 이상 있었는 지
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	LocalDateTime lastUsedDate; 							// 실제 마지막 사용 시간 :: 푸시 3번 이상 터치에 대한 업데이트

}

