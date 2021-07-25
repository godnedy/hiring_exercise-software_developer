package com.edytagodniak.hiring_exercisesoftware_developer.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Constraint(validatedBy = MinSizeValidator.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface MinSize {
    String message() default "The input list cannot contain lees than 2 elements.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
