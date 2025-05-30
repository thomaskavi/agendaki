package com.thomaskavi.agendaki.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Documented
@Constraint(validatedBy = EndTimeAfterStartTimeValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface EndTimeAfterStartTime {
    String message() default "A hora de fim deve ser após a hora de início";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
