package com.c104.seolo.domain.user.exception.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ComplexPasswordValidator implements ConstraintValidator<PasswordConstraint, String> {
    @Override
    public void initialize(PasswordConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            setContextMessage(context, "비밀번호를 입력해주세요");
            return false;
        }
        if (value.length() < 8 || value.length() > 16) {
            setContextMessage(context, "비밀번호는 8~16자 사이여야 합니다.");
            return false;
        }
        if (!value.matches(".*[A-Z].*")) {
            setContextMessage(context, "비밀번호는 최소 한 개의 대문자를 포함해야 합니다.");
            return false;
        }
        if (!value.matches(".*[a-z].*")) {
            setContextMessage(context, "비밀번호는 최소 한 개의 소문자를 포함해야 합니다.");
            return false;
        }
        if (!value.matches(".*\\d.*")) {
            setContextMessage(context, "비밀번호는 최소 한 개의 숫자를 포함해야 합니다.");
            return false;
        }
        if (!value.matches(".*[~!@#$%^&*()+|=].*")) {
            setContextMessage(context, "비밀번호는 최소 한 개의 특수 문자를 포함해야 합니다.");
            return false;
        }
        return true;
    }

    private void setContextMessage(ConstraintValidatorContext context, String message) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
    }
}
