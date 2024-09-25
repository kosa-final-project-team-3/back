package com.kosa.jungdoin.entity;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "expert_reviews")
public class ExpertReview extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "expert_review_seq")
	private Long expertReviewSeq;
	@ManyToOne
	@JoinColumn(name = "expert_id", nullable = false)
	private Expert expert;
	@ManyToOne
	@JoinColumn(name = "member_id", nullable = false)
	private Member member;
	@Column(name = "satisfaction", precision = 2, scale = 1, nullable = false)
	private BigDecimal satisfaction;
	@Column(name = "expertise", precision = 2, scale = 1, nullable = false)
	private BigDecimal expertise;
	@Column(name = "kindness", precision = 2, scale = 1, nullable = false)
	private BigDecimal kindness;
	@Column(name = "punctuality", precision = 2, scale = 1, nullable = false)
	private BigDecimal punctuality;
	@Column(name = "passion", precision = 2, scale = 1, nullable = false)
	private BigDecimal passion;
	@Column(name = "comment", length = 2000, nullable = false)
	private String comment;
}
