package com.kosa.jungdoin.lesson.common;

import com.kosa.jungdoin.entity.Trainer;
import com.kosa.jungdoin.trainer.repository.TrainerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.function.Function;

@Transactional
@RequiredArgsConstructor
public abstract class BaseLessonService<T, D, B> {

    protected final JpaRepository<T, Long> repository;
    protected final TrainerRepository trainerRepository;

    @Transactional(readOnly = true)
    public D getLesson(Long lessonId) {
        T lesson = repository.findById(lessonId)
                .orElseThrow(() -> new IllegalArgumentException("Lesson not found"));
        return convertToDTO(lesson);
    }

    @Transactional(readOnly = true)
    public List<D> getAllLessons() {
        return repository.findAll().stream()
                .map(this::convertToDTO)
                .toList();
    }

    public D createLesson(D dto) {
        T entity = createEntityFromDTO(dto);
        T savedEntity = repository.save(entity);
        return convertToDTO(savedEntity);
    }

    public D updateLesson(D dto) {
        T updatedEntity = updateEntityFromDTO(dto);
        T savedEntity = repository.save(updatedEntity);
        return convertToDTO(savedEntity);
    }

    public D patchLesson(D dto) {
        T existingEntity = getExistEntity(dto);
        T patchedEntity = patchEntityFromDTO(existingEntity, dto);
        T savedEntity = repository.save(patchedEntity);
        return convertToDTO(savedEntity);
    }

    public void deleteLesson(Long lessonId) {
        if (!repository.existsById(lessonId)) {
            throw new IllegalArgumentException("Lesson not found");
        }
        repository.deleteById(lessonId);
    }

    protected abstract D convertToDTO(T lesson);

    protected abstract T createEntityFromDTO(D dto);

    protected abstract T updateEntityFromDTO(D dto);

    protected Trainer getTrainerIfIdPresent(Long trainerId) {
        if (trainerId != null) {
            return trainerRepository.findById(trainerId)
                    .orElseThrow(() -> new IllegalArgumentException("Trainer not found"));
        }
        return null;
    }

    protected abstract B getBuilder();

    protected abstract Function<B, T> getBuildFunction();

    protected T createEntityFromBuilder(B builder) {
        return getBuildFunction().apply(builder);
    }

    protected B initializeBuilder(T entity) {
        B builder = getBuilder();
        setCommonFields(builder, entity);
        return builder;
    }

    protected abstract void setCommonFields(B builder, T entity);

    protected T patchEntityFromDTO(T entity, D dto) {
        B builder = initializeBuilder(entity);
        updateBuilderFromDTO(builder, dto);
        return createEntityFromBuilder(builder);
    }

    protected abstract void updateBuilderFromDTO(B builder, D dto);

    protected abstract T getExistEntity(D dto);
}
