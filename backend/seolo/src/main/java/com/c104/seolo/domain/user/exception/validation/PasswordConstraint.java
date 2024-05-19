package com.c104.seolo.domain.user.exception.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ComplexPasswordValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface PasswordConstraint {
    String message() default "비밀번호 검증 실패";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
