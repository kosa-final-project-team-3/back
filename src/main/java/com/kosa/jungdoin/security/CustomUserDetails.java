package com.kosa.jungdoin.security;

import com.kosa.jungdoin.common.Role;
import com.kosa.jungdoin.entity.Member;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CustomUserDetails implements UserDetails {
	private Long id;
	private Role role;
	private String socialType;
	private String memberOAuthId;
	private String username;
	private LocalDate birth;
	private String gender;
	private String phone;
	private Boolean trial;
	private String address;
	private BigDecimal tall;

	@Builder
	public CustomUserDetails(
		String username, Role role, String socialType, String memberOAuthId,
		String gender, String phone, Boolean trial,
		String address, BigDecimal tall
	) {
		this.username = username;
		this.role = role;
		this.socialType = socialType;
		this.memberOAuthId = memberOAuthId;
		this.gender = gender;
		this.phone = phone;
		this.trial = trial;
		this.address = address;
		this.tall = tall;
	}

	public CustomUserDetails(Member member) {
		this.id = member.getMemberId();
		this.role = member.getRole();
		this.birth = member.getBirth();
		this.gender = member.getGender();
		this.phone = member.getPhone();
		this.trial = member.getTrial();
		this.address = member.getAddress();
		this.tall = member.getTall();
	}

	public CustomUserDetails(Role role, String socialType, String memberOAuthId) {
		this.username = memberOAuthId;
		this.role = role;
		this.socialType = socialType;
		this.memberOAuthId = memberOAuthId;
	}

	public static CustomUserDetails of(Member member) {
		return new CustomUserDetails(member);
	}

	public static CustomUserDetails of(Role role, String socialType, String memberOAuthId) {
		return new CustomUserDetails(role, socialType, memberOAuthId);
	}

	// CustomUserDetails 의 정보를 통해 엔티티를 생성한다.
	public Member newEntity() {
		return Member.builder()
			.username(username)
			.role(role)
			.socialType(socialType)
			.memberOAuthId(memberOAuthId)
			.build();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Collections.singleton(new SimpleGrantedAuthority("REGULAR"));
	}

	@Override
	public String getPassword() {
		return "";
	}

	@Override
	public String getUsername() {
		return this.username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
