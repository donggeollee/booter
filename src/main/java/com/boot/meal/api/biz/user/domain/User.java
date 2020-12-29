package com.boot.meal.api.biz.user.domain;

import com.boot.meal.api.biz.notification.domain.Notification;
import com.boot.meal.common.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
//public class Member extends BaseEntity implements UserDetails { // 시큐리티 적용 시 고려
public class User extends BaseEntity {

//	@Entity
//		테이블과 링크될 클래스임을 나타냅니다.
//		언더스코어 네이밍(_)으로 이름을 매칭합니다.
//		ex) SalesManager.java -> sales_manager table
//	@Id
//		해당 테이블의 PK 필드를 나타냅니다.
//	@GeneratedValue
//		PK의 생성 규칙을 나타냅니다.
//		기본값은 AUTO 로, MySQL의 auto_increment와 같이 자동증가하는 정수형 값이 됩니다.
//		스프링 부트 2.0에선 옵션을 추가하셔야만 auto_increment가 됩니다!
//	@Column
//		테이블의 컬럼을 나타내면, 굳이 선언하지 않더라도 해당 클래스의 필드는 모두 컬럼이 됩니다.
//		사용하는 이유는, 기본값 외에 추가로 변경이 필요한 옵션이 있을경우 사용합니다.
//		문자열의 경우 VARCHAR(255)가 기본값인데, 사이즈를 500으로 늘리고 싶거나(ex: title), 타입을 TEXT로 변경하고 싶거나(ex: content) 등의 경우에 사용됩니다.
//  @ManyToOne,  @OneToMany, @OneToOne
//   	테이블 간 관계 설정

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Long id;
	@NotBlank
	String email;
	@NotBlank
	String signupType; 									// ENUM :: email, naver, kakao, google
	@NotBlank
	String password;
	String phone1;
	String phone2;
	String phone3;
	@NotBlank
	String gender;
	String ip;
	boolean isDelete = false;
	// 이미 이용중인 서비스가 있는 경우 나중에 등록한 서비스를 통해 하나만 정하도록 유도
	boolean isMobile;									// 모바일 앱 사용자 => 푸시알림
	boolean isWeb;										// 웹앱 사용자 => 메일로 알림에 대한 안내 후 페이지 내에서 하나씩 추천.

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	LocalDateTime lastLoginDate;

	@OneToOne
	@JoinColumn(name="id")
	private UserNotificationSetting userNotificationSetting;
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
	private List<Notification> notification;
}

