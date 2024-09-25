package com.kosa.jungdoin.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "experts")
public class Expert extends Member {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "expert_id")
	private Long expertId;
	@ManyToOne
	@JoinColumn(name = "exercise_category_code", nullable = false)
	private ExerciseCategory exerciseCategory;
}