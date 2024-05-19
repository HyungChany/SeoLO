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
        String errorMessage = null; // 에러 메시지를 저장할 변수 초기화

        if (value == null) {
            errorMessage = "비밀번호를 입력해주세요";
        } else {
            if (value.length() < 8 || value.length() > 16) {
                errorMessage = "비밀번호는 8~16자 사이여야 합니다.";
            } else if (!value.matches(".*[A-Z].*")) {
                errorMessage = "비밀번호는 최소 한 개의 대문자를 포함해야 합니다.";
            } else if (!value.matches(".*[a-z].*")) {
                errorMessage = "비밀번호는 최소 한 개의 소문자를 포함해야 합니다.";
            } else if (!value.matches(".*\\d.*")) {
                errorMessage = "비밀번호는 최소 한 개의 숫자를 포함해야 합니다.";
            } else if (!value.matches(".*[~!@#$%^&*()+|=].*")) {
                errorMessage = "비밀번호는 최소 한 개의 특수 문자를 포함해야 합니다.";
            }
        }

        if (errorMessage != null) {
            setContextMessage(context, errorMessage);
            return false;
        }

        return true;
    }

    private void setContextMessage(ConstraintValidatorContext context, String message) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
    }
}
