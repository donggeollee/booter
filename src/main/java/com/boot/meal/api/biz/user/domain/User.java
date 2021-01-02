package com.boot.meal.api.biz.user.domain;

import com.boot.meal.api.biz.notification.domain.Notification;
import com.boot.meal.api.biz.user.domain.dto.UserRequestDTO;
import com.boot.meal.common.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;


@Builder
@AllArgsConstructor
@ToString
@Getter
@Setter
@Entity
public class User extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(length = 50, unique = true)
	@NotBlank
	@Size(min = 4, max = 50)
	private String  email;

	@Column(length = 100)
	@NotBlank
	@Size(min = 4, max = 100)
	private String  password;

	@Column(length = 50)
	@NotBlank
	@Size(min = 4, max = 50)
	private String  role;

	@Column(length = 50)
	@NotBlank
	@Size(min = 4, max = 50)
	private String country;

	@NotBlank
	private String  signupType;			// ENUM :: email, naver, kakao, google
	private String  phone1;
	private String  phone2;
	private String  phone3;
	@NotBlank
	private String  gender;
	private String  ip;
	private boolean isDelete = false;
	// 이미 이용중인 서비스가 있는 경우 나중에 등록한 서비스를 통해 하나만 정하도록 유도
	private boolean isMobile;									// 모바일 앱 사용자 => 푸시알림
	private boolean isWeb;										// 웹앱 사용자 => 메일로 알림에 대한 안내 후 페이지 내에서 하나씩 추천.

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime lastLoginDate;

	@OneToOne
	@JoinColumn(name="id")
	private UserNotificationSetting userNotificationSetting;
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
	private List<Notification> notificationList;

	public static User of(UserRequestDTO userRequestDTO){
		return User.builder()
				.id(userRequestDTO.getId())
				.email(userRequestDTO.getEmail())
				.password(userRequestDTO.getPassword())
				.signupType(userRequestDTO.getSignupType())
				.country(userRequestDTO.getCountry())
				.phone1(userRequestDTO.getPhone1())
				.phone2(userRequestDTO.getPhone2())
				.phone3(userRequestDTO.getPhone3())
				.gender(userRequestDTO.getGender())
				.ip(userRequestDTO.getIp())
				.isDelete(userRequestDTO.isDelete())
				.isMobile(userRequestDTO.isMobile())
				.isWeb(userRequestDTO.isWeb())
				.lastLoginDate(userRequestDTO.getLastLoginDate())
				.userNotificationSetting(userRequestDTO.getUserNotificationSetting())
				.notificationList(userRequestDTO.getNotificationList())
				.build();
	}

}

