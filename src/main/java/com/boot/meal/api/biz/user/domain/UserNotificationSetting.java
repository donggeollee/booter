package com.boot.meal.api.biz.user.domain;

import com.boot.meal.common.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @ 사용자 푸시 알림 정보
 * 사용자가 아무런 설정 하지 않았을 시 기본값 필요.
 */
@Getter
@NoArgsConstructor
@Entity
public class UserNotificationSetting extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Long id;

	boolean isMonday;
	boolean isTuesday;
	boolean isWednesday;
	boolean isThursday;
	boolean isFriday;
	boolean isSaturday;
	boolean isSunday;
	boolean isEarlyNotification; 									// 식사시간 15분전 알림설정

	// 기본 시간 어떻게 정할 것인지 <= 배치 아키텍쳐 고민
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	LocalDateTime mondayLaunchPushTime; 							// 월요일 점심 추천 시간
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	LocalDateTime mondayDinnerPushTime;								// 월요일 저녁 추천 시간

//	@OneToOne(mappedBy = "user_notification_setting")
//	User user;

}

