package com.boot.meal.api.biz.user.domain.dto;

import com.boot.meal.api.biz.notification.domain.Notification;
import com.boot.meal.api.biz.user.domain.UserNotificationSetting;
import com.boot.meal.security.Role;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class UserRequestDTO {

	private Long id;
	private String email;
	private String signupType; 									// ENUM :: email, naver, kakao, google
	private String password;
	private Role role;
	private String country;
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

	private List<UserNotificationSetting> userNotificationSettingList;
	private List<Notification> notificationList;


	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

	public void setEncodedPassword(String encodedPassword){
		this.password = encodedPassword;
	}
	public void setRole(Role role){
		this.role = role;
	}
	/*
	public static UserRequestDTO of(User user){
		return UserRequestDTO.builder()
				.id(user.getId())
				.email(user.getEmail())
				.password(user.getPassword())
				.signupType(user.getSignupType())
				.phone1(user.getPhone1())
				.phone2(user.getPhone2())
				.phone3(user.getPhone3())
				.gender(user.getGender())
				.ip(user.getIp())
				.isDelete(user.isDelete())
				.isMobile(user.isMobile())
				.isWeb(user.isWeb())
				.lastLoginDate(user.getLastLoginDate())
				.userNotificationSetting(user.getUserNotificationSetting())
				.notificationList(user.getNotificationList())
				.build();
	}
	 */
}

