package com.kosa.jungdoin.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "time_tables")
public class TimeTable extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "time_table_id")
	private Long timeTableId;
	@ManyToOne
	@JoinColumn(name = "expert_id", nullable = false)
	private Expert expert;
	@Column(name = "day", length = 2, nullable = false)
	private String day;
	@Column(name = "start_time", nullable = false)
	private LocalDateTime startTime;
	@Column(name = "end_time", nullable = false)
	private LocalDateTime endTime;
}