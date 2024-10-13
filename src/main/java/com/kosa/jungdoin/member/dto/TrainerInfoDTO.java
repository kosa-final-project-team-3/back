package com.kosa.jungdoin.member.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrainerInfoDTO {
	private Long trainerProfileId;
	private Long trainerId;
	private String categoryCode;
	private String categoryName;
	private String title;
	private LocalDate startDate;
	private LocalDate endDate;
	private String detail;

	private String exerciseCategoryCode;
	private String exerciseCategoryName;
}
