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
@Table(name = "schedules")
public class Schedule extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "schedule_seq")
	private Long scheduleSeq;
	@ManyToOne
	@JoinColumn(name = "expert_id", nullable = false)
	private Expert expert;
	@ManyToOne
	@JoinColumn(name = "contract_id", nullable = false)
	private Contract contract;
	@Column(name = "start_time", nullable = false)
	private LocalDateTime startTime;
	@Column(name = "end_time", nullable = false)
	private LocalDateTime endTime;
}