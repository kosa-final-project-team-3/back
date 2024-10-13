package com.kosa.jungdoin.lesson.personal_lesson.service;

import com.kosa.jungdoin.common.annotation.ValidateTrainer;
import com.kosa.jungdoin.entity.PersonalLesson;
import com.kosa.jungdoin.entity.Trainer;
import com.kosa.jungdoin.lesson.common.BaseLessonService;
import com.kosa.jungdoin.lesson.personal_lesson.dto.PersonalLessonDTO;
import com.kosa.jungdoin.lesson.personal_lesson.repository.PersonalLessonRepository;
import com.kosa.jungdoin.trainer.repository.TrainerRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.function.Function;

@Service
@Transactional
public class PersonalLessonService
        extends BaseLessonService<PersonalLesson, PersonalLessonDTO, PersonalLesson.PersonalLessonBuilder<?, ?>> {

    public PersonalLessonService(PersonalLessonRepository repository, TrainerRepository trainerRepository) {
        super(repository, trainerRepository);
    }

    @Override
    protected PersonalLessonDTO convertToDTO(PersonalLesson lesson) {
        return PersonalLessonDTO.builder()
                .lessonId(lesson.getPersonalLessonId())
                .trainerId(lesson.getTrainer().getMemberId())
                .title(lesson.getTitle())
                .content(lesson.getContent())
                .price(lesson.getPrice())
                .lat(lesson.getLat())
                .lng(lesson.getLng())
                .build();
    }

    @Override
    protected PersonalLesson.PersonalLessonBuilder<?, ?> getBuilder() {
        return PersonalLesson.builder();
    }

    @Override
    protected Function<PersonalLesson.PersonalLessonBuilder<?, ?>, PersonalLesson> getBuildFunction() {
        return PersonalLesson.PersonalLessonBuilder::build;
    }

    @Override
    protected void setCommonFields(PersonalLesson.PersonalLessonBuilder<?, ?> builder, PersonalLesson entity) {
        builder.personalLessonId(entity.getPersonalLessonId())
                .trainer(entity.getTrainer())
                .title(entity.getTitle())
                .content(entity.getContent())
                .price(entity.getPrice())
                .lat(entity.getLat())
                .lng(entity.getLng());
    }

    @Override
    @ValidateTrainer
    protected void updateBuilderFromDTO(PersonalLesson.PersonalLessonBuilder<?, ?> builder, PersonalLessonDTO dto) {
        Trainer updatedTrainer = getTrainerIfIdPresent(dto.getTrainerId());
        if (updatedTrainer != null)
            builder.trainer(updatedTrainer);
        if (dto.getTitle() != null)
            builder.title(dto.getTitle());
        if (dto.getContent() != null)
            builder.content(dto.getContent());
        if (dto.getPrice() != null)
            builder.price(dto.getPrice());
        if (dto.getLat() != null)
            builder.lat(dto.getLat());
        if (dto.getLng() != null)
            builder.lng(dto.getLng());
    }

    @Override
    protected PersonalLesson getExistEntity(PersonalLessonDTO dto) {
        return repository.findById(dto.getLessonId()).orElseThrow(
                () -> new EntityNotFoundException("Lesson not found")
        );
    }

    @Override
    @ValidateTrainer
    protected PersonalLesson createEntityFromDTO(PersonalLessonDTO dto) {
        Trainer trainer = trainerRepository.findById(dto.getTrainerId()).get();
        return PersonalLesson.builder()
                .trainer(trainer)
                .title(dto.getTitle())
                .content(dto.getContent())
                .price(dto.getPrice())
                .lat(dto.getLat())
                .lng(dto.getLng())
                .build();
    }

    @Override
    @ValidateTrainer
    protected PersonalLesson updateEntityFromDTO(PersonalLessonDTO dto) {
        Trainer trainer = trainerRepository.findById(dto.getTrainerId()).get();
        return PersonalLesson.builder()
                .personalLessonId(dto.getLessonId())
                .trainer(trainer)
                .title(dto.getTitle())
                .content(dto.getContent())
                .price(dto.getPrice())
                .lat(dto.getLat())
                .lng(dto.getLng())
                .build();
    }
}
