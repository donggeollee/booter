package com.boot.test.api.domain;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Data
@Document(collation = "member")
public class Member extends User {

	public Member(String username, String password, Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);
	}

	String id;
	private String name;
	private String phone;
	private String age;
	private String gender;


}
