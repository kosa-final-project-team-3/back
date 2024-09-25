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
@Table(name = "online_lessons")
public class OnlineLesson extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "online_lesson_id")
	private Long onlineLessonId;
	@ManyToOne
	@JoinColumn(name = "expert_id", nullable = false)
	private Expert expert;
	@Column(name = "title", length = 20, nullable = false)
	private String title;
	@Column(name = "content", length = 2000, nullable = false)
	private String content;
	@Column(name = "price", nullable = false)
	private Integer price;
}
