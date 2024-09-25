package com.kosa.jungdoin.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;

public class BaseMember extends BaseEntity {
	@Column(name = "member_oauth_id", nullable = false)
	private String memberOauthId;
	@Column(name = "social_type", length = 20, nullable = false)
	private String socialType;
	@Column(name = "role", length = 10, nullable = false)
	private String role;
	@Column(name = "username", length = 10, nullable = false)
	private String username;
	@Column(name = "birth", length = 10, nullable = false)
	private LocalDate birth;
	@Column(name = "gender", length = 1, nullable = false)
	private String gender;
	@Column(name = "phone", length = 13, nullable = false)
	private String phone;
	@Column(name = "point", nullable = false)
	private Integer point;
}