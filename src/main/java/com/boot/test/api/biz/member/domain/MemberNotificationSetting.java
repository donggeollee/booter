package com.boot.test.api.biz.member.domain;

import com.boot.test.common.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

/**
 * @ 사용자 푸시 알림 정보
 * 사용자가 아무런 설정 하지 않았을 시 기본값 필요.
 */
@Getter
@Entity
public class MemberNotificationSetting extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Long id;
	Long memberId;
	boolean isMonday;
	boolean isTuesday;
	boolean isWednesday;
	boolean isThursday;
	boolean isFriday;
	boolean isSaturday;
	boolean isSunday;

	// 기본 시간 어떻게 정할 것인지 <= 배치 아키텍쳐 고민
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	LocalDateTime mondayBreakfastPushTime = LocalDateTime.now(); 						// 월요일 아침 추천 시간
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	LocalDateTime mondayLaunchPushTime = LocalDateTime.now(); 							// 월요일 점심 추천 시간
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	LocalDateTime mondayDinnerPushTime;


}

