package com.kosa.jungdoin.entity;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDate;

@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public abstract class BaseEntity {
	@CreatedDate
	@Column(name = "created_at", updatable = false)
	private LocalDate createdAt;
	@LastModifiedDate
	@Column(name = "updated_at")
	private LocalDate updatedAt;
}
