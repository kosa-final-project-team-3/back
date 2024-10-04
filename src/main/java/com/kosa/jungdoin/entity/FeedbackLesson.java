package com.kosa.jungdoin.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = "feedback_lessons")
public class FeedbackLesson extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "feedback_lesson_id")
	private Long feedbackLessonId;
	@ManyToOne
	@JoinColumn(name = "member_id", nullable = false)
	private Trainer trainer;
	@Column(name = "content", length = 2000, nullable = false)
	private String content;
	@Column(name = "price", nullable = false)
	private Integer price;
}