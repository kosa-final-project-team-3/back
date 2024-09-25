package com.kosa.jungdoin.entity;

import java.time.LocalDate;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity
public class BaseEntity {
	@CreatedDate
	@Column(name = "created_at")
	private LocalDate createdAt;
	@LastModifiedDate
	@Column(name = "updated_at")
	private LocalDate updatedAt;
}
