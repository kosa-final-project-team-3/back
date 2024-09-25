package com.kosa.jungdoin.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Asks")
public class Ask extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ask_id")
	private Long askId;
	@Column(name = "title", length = 20, nullable = false)
	private String title;
	@Column(name = "content", length = 2000, nullable = false)
	private String content;
	@Column(name = "email", length = 20, nullable = false)
	private String email;
	@Column(name = "state", length = 10, nullable = false)
	private String state;
}
