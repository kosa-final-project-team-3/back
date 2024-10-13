package com.kosa.jungdoin.lesson.online_lesson.service;

import com.kosa.jungdoin.common.annotation.ValidateTrainer;
import com.kosa.jungdoin.entity.OnlineLesson;
import com.kosa.jungdoin.entity.Trainer;
import com.kosa.jungdoin.lesson.common.BaseLessonService;
import com.kosa.jungdoin.lesson.online_lesson.dto.OnlineLessonDTO;
import com.kosa.jungdoin.lesson.online_lesson.repository.OnlineLessonRepository;
import com.kosa.jungdoin.trainer.repository.TrainerRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.function.Function;

@Service
@Transactional
public class OnlineLessonService
        extends BaseLessonService<OnlineLesson, OnlineLessonDTO, OnlineLesson.OnlineLessonBuilder<?, ?>> {

    public OnlineLessonService(OnlineLessonRepository repository, TrainerRepository trainerRepository) {
        super(repository, trainerRepository);
    }

    @Override
    protected OnlineLessonDTO convertToDTO(OnlineLesson lesson) {
        return OnlineLessonDTO.builder()
                .lessonId(lesson.getOnlineLessonId())
                .trainerId(lesson.getTrainer().getMemberId())
                .title(lesson.getTitle())
                .content(lesson.getContent())
                .price(lesson.getPrice())
                .build();
    }

    @Override
    protected OnlineLesson.OnlineLessonBuilder<?, ?> getBuilder() {
        return OnlineLesson.builder();
    }

    @Override
    protected Function<OnlineLesson.OnlineLessonBuilder<?, ?>, OnlineLesson> getBuildFunction() {
        return OnlineLesson.OnlineLessonBuilder::build;
    }

    @Override
    protected void setCommonFields(OnlineLesson.OnlineLessonBuilder<?, ?> builder, OnlineLesson entity) {
        builder.onlineLessonId(entity.getOnlineLessonId())
                .trainer(entity.getTrainer())
                .title(entity.getTitle())
                .content(entity.getContent())
                .price(entity.getPrice());
    }

    @Override
    @ValidateTrainer
    protected void updateBuilderFromDTO(OnlineLesson.OnlineLessonBuilder<?, ?> builder, OnlineLessonDTO dto) {
        Trainer updatedTrainer = getTrainerIfIdPresent(dto.getTrainerId());
        if (updatedTrainer != null)
            builder.trainer(updatedTrainer);
        if (dto.getTitle() != null)
            builder.title(dto.getTitle());
        if (dto.getContent() != null)
            builder.content(dto.getContent());
        if (dto.getPrice() != null)
            builder.price(dto.getPrice());
    }

    @Override
    protected OnlineLesson getExistEntity(OnlineLessonDTO dto) {
        return repository.findById(dto.getLessonId()).orElseThrow(
                () -> new EntityNotFoundException("Lesson not found")
        );
    }

    @Override
    @ValidateTrainer
    protected OnlineLesson createEntityFromDTO(OnlineLessonDTO dto) {
        Trainer trainer = trainerRepository.findById(dto.getTrainerId()).get();
        return OnlineLesson.builder()
                .trainer(trainer)
                .title(dto.getTitle())
                .content(dto.getContent())
                .price(dto.getPrice())
                .build();
    }

    @Override
    @ValidateTrainer
    protected OnlineLesson updateEntityFromDTO(OnlineLessonDTO dto) {
        Trainer trainer = trainerRepository.findById(dto.getTrainerId()).get();
        return OnlineLesson.builder()
                .onlineLessonId(dto.getLessonId())
                .trainer(trainer)
                .title(dto.getTitle())
                .content(dto.getContent())
                .price(dto.getPrice())
                .build();
    }
}
