package com.kosa.jungdoin.lesson.group_lesson.service;

import com.kosa.jungdoin.common.annotation.ValidateTrainer;
import com.kosa.jungdoin.entity.GroupLesson;
import com.kosa.jungdoin.entity.Trainer;
import com.kosa.jungdoin.lesson.common.BaseLessonService;
import com.kosa.jungdoin.lesson.group_lesson.dto.GroupLessonDTO;
import com.kosa.jungdoin.lesson.group_lesson.repository.GroupLessonRepository;
import com.kosa.jungdoin.trainer.repository.TrainerRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.function.Function;

@Service
@Transactional
public class GroupLessonService
        extends BaseLessonService<GroupLesson, GroupLessonDTO, GroupLesson.GroupLessonBuilder<?, ?>> {

    public GroupLessonService(GroupLessonRepository repository, TrainerRepository trainerRepository) {
        super(repository, trainerRepository);
    }

    @Override
    protected GroupLessonDTO convertToDTO(GroupLesson lesson) {
        return GroupLessonDTO.builder()
                .lessonId(lesson.getGroupLessonId())
                .trainerId(lesson.getTrainer().getMemberId())
                .maxCnt(lesson.getMaxCnt())
                .startDate(lesson.getStartDate())
                .startEnd(lesson.getStartEnd())
                .done(lesson.getDone())
                .content(lesson.getContent())
                .title(lesson.getTitle())
                .price(lesson.getPrice())
                .location(lesson.getLocation())
                .lat(lesson.getLat())
                .lng(lesson.getLng())
                .build();
    }

    @Override
    protected GroupLesson.GroupLessonBuilder<?, ?> getBuilder() {
        return GroupLesson.builder();
    }

    @Override
    protected Function<GroupLesson.GroupLessonBuilder<?, ?>, GroupLesson> getBuildFunction() {
        return GroupLesson.GroupLessonBuilder::build;
    }

    @Override
    protected void setCommonFields(GroupLesson.GroupLessonBuilder<?, ?> builder, GroupLesson entity) {
        builder.groupLessonId(entity.getGroupLessonId())
                .trainer(entity.getTrainer())
                .maxCnt(entity.getMaxCnt())
                .startDate(entity.getStartDate())
                .startEnd(entity.getStartEnd())
                .done(entity.getDone())
                .content(entity.getContent())
                .title(entity.getTitle())
                .price(entity.getPrice())
                .location(entity.getLocation())
                .lat(entity.getLat())
                .lng(entity.getLng());
    }

    @Override
    @ValidateTrainer
    protected void updateBuilderFromDTO(GroupLesson.GroupLessonBuilder<?, ?> builder, GroupLessonDTO dto) {
        Trainer updatedTrainer = getTrainerIfIdPresent(dto.getTrainerId());
        if (updatedTrainer != null)
            builder.trainer(updatedTrainer);
        if (dto.getMaxCnt() != null)
            builder.maxCnt(dto.getMaxCnt());
        if (dto.getStartDate() != null)
            builder.startDate(dto.getStartDate());
        if (dto.getStartEnd() != null)
            builder.startEnd(dto.getStartEnd());
        if (dto.getDone() != null)
            builder.done(dto.getDone());
        if (dto.getContent() != null)
            builder.content(dto.getContent());
        if (dto.getTitle() != null)
            builder.title(dto.getTitle());
        if (dto.getPrice() != null)
            builder.price(dto.getPrice());
        if (dto.getLocation() != null)
            builder.location(dto.getLocation());
        if (dto.getLat() != null)
            builder.lat(dto.getLat());
        if (dto.getLng() != null)
            builder.lng(dto.getLng());
    }

    @Override
    protected GroupLesson getExistEntity(GroupLessonDTO dto) {
        return repository.findById(dto.getLessonId()).orElseThrow(
                () -> new EntityNotFoundException("Lesson not found")
        );
    }

    @Override
    @ValidateTrainer
    protected GroupLesson createEntityFromDTO(GroupLessonDTO dto) {
        Trainer trainer = trainerRepository.findById(dto.getTrainerId()).get();
        return GroupLesson.builder()
                .trainer(trainer)
                .maxCnt(dto.getMaxCnt())
                .startDate(dto.getStartDate())
                .startEnd(dto.getStartEnd())
                .done(dto.getDone())
                .content(dto.getContent())
                .title(dto.getTitle())
                .price(dto.getPrice())
                .location(dto.getLocation())
                .lat(dto.getLat())
                .lng(dto.getLng())
                .build();
    }

    @Override
    @ValidateTrainer
    protected GroupLesson updateEntityFromDTO(GroupLessonDTO dto) {
        Trainer trainer = trainerRepository.findById(dto.getTrainerId()).get();
        return GroupLesson.builder()
                .groupLessonId(dto.getLessonId())
                .trainer(trainer)
                .maxCnt(dto.getMaxCnt())
                .startDate(dto.getStartDate())
                .startEnd(dto.getStartEnd())
                .done(dto.getDone())
                .content(dto.getContent())
                .title(dto.getTitle())
                .price(dto.getPrice())
                .location(dto.getLocation())
                .lat(dto.getLat())
                .lng(dto.getLng())
                .build();
    }
}
