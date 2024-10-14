package com.kosa.jungdoin.member.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kosa.jungdoin.entity.TrainerProfile;

public interface TrainerProfileRepository extends JpaRepository<TrainerProfile, Long> {
	List<TrainerProfile> findByTrainerMemberId(Long memberId);
}
