package com.kosa.jungdoin.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "experts")
public class Expert extends Member {
	@ManyToOne
	@JoinColumn(name = "exercise_category_code", nullable = false)
	private ExerciseCategory exerciseCategory;
}