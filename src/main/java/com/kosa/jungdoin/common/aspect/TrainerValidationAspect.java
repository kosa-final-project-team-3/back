package com.kosa.jungdoin.common.aspect;

import com.kosa.jungdoin.trainer.repository.TrainerRepository;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Aspect
@Component
public class TrainerValidationAspect {

    private final TrainerRepository trainerRepository;

    public TrainerValidationAspect(TrainerRepository trainerRepository) {
        this.trainerRepository = trainerRepository;
    }

    @Around("@annotation(com.kosa.jungdoin.common.annotation.ValidateTrainer) && args(..)")
    public Object validateTrainer(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs(); // 메소드의 모든 파라미터를 가져옴

        // 각 파라미터에서 trainerId를 추출
        for (Object arg : args) {
            Long trainerId = extractTrainerId(arg);
            if (trainerId != null && !trainerRepository.existsById(trainerId)) {
                throw new IllegalArgumentException("Trainer not found");
            }
        }

        return joinPoint.proceed();
    }

    private Long extractTrainerId(Object arg) {
        try {
            Method getTrainerIdMethod = arg.getClass().getMethod("getTrainerId");
            return (Long) getTrainerIdMethod.invoke(arg);  // getter 메소드 호출
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            // getTrainerId 메소드가 없거나 호출 불가능한 경우 null 반환
            return null;
        }
    }
}
