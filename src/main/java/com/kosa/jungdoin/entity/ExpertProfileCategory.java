package com.kosa.jungdoin.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "expert_profile_categories")
public class ExpertProfileCategory {
	@Id
	@Column(name = "category_code", length = 10, nullable = false)
	private String categoryCode;
	@Column(name = "category_name", length = 20, nullable = false)
	private String categoryName;
}