package com.kosa.jungdoin.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "exercise_categories")
public class ExerciseCategory {
	@Id
	@Column(name = "exercise_category_code", length = 10, nullable = false)
	private String exerciseCategoryCode;
	@Column(name = "category_name", length = 20, nullable = false)
	private String categoryName;
}