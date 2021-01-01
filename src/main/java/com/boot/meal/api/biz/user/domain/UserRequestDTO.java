package com.boot.meal.api.biz.user.domain;

import com.boot.meal.api.biz.notification.domain.Notification;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class UserRequestDTO {

	private Long id;
	private String email;
	private String signupType; 									// ENUM :: email, naver, kakao, google
	private String password;
	private String phone1;
	private String phone2;
	private String phone3;
	private String gender;
	private String ip;
	private boolean isDelete = false;
	private boolean isMobile;
	private boolean isWeb;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime lastLoginDate;

	private UserNotificationSetting userNotificationSetting;
	private List<Notification> notification;
}

