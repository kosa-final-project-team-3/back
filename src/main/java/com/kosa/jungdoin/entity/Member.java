package com.kosa.jungdoin.entity;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "members")
public class Member extends BaseMember {
	@OneToOne
	@JoinColumn(name = "member_id", nullable = false)
	private Long memberId;
	@Column(name = "body_weight", precision = 4, scale = 1, nullable = false)
	private BigDecimal bodyWeight;
	@Column(name = "tall", precision = 4, scale = 1, nullable = false)
	private BigDecimal tall;
	@Column(name = "address", nullable = false)
	private String address;
}
