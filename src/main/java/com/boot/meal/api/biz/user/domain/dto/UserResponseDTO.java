package com.boot.meal.api.biz.user.domain.dto;

import com.boot.meal.api.biz.notification.domain.Notification;
import com.boot.meal.api.biz.user.domain.User;
import com.boot.meal.api.biz.user.domain.UserNotificationSetting;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class UserResponseDTO {

	private Long id;
	private String email;
	private String signupType; 									// ENUM :: email, naver, kakao, google
	private String role;
	private String country;
	private String password;
	private String phone1;
	private String phone2;
	private String phone3;
	private String gender;
	private String ip;
	private boolean isDelete = false;
	private boolean isMobile;
	private boolean isWeb;
	private String jwtToken;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime lastLoginDate;

	private List<UserNotificationSetting> userNotificationSettingList;
	private List<Notification> notificationList;

	public static UserResponseDTO of(User user){
		return UserResponseDTO.builder()
				.id(user.getId())
				.email(user.getEmail())
				.signupType(user.getSignupType())
				.role(user.getRole())
				.country(user.getCountry())
				.phone1(user.getPhone1())
				.phone2(user.getPhone2())
				.phone3(user.getPhone3())
				.gender(user.getGender())
				.ip(user.getIp())
				.isDelete(user.isDelete())
				.isMobile(user.isMobile())
				.isWeb(user.isWeb())
				.build();
	}

	public void setJwtToken(String jwtToken){
		this.jwtToken = jwtToken;
	}



}

