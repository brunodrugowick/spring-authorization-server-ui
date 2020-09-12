package dev.drugowick.springauthorizationserverui.web.dto.validation;

import org.springframework.beans.BeanUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.ValidationException;
import java.lang.reflect.InvocationTargetException;
import java.util.Objects;

public class NewPasswordFieldsMustMatchValidator implements ConstraintValidator<NewPasswordFieldsMustMatch, Object> {

    private String fieldNewPassword;
    private String fieldNewPasswordConfirmation;

    @Override
    public void initialize(NewPasswordFieldsMustMatch constraintAnnotation) {
        this.fieldNewPassword = constraintAnnotation.fieldNewPassword();
        this.fieldNewPasswordConfirmation = constraintAnnotation.fieldNewPasswordConfirmation();
    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        String currentPassword;
        String currentPasswordConfirmation;

        try {
            currentPassword = (String) Objects.requireNonNull(BeanUtils.getPropertyDescriptor(o.getClass(), this.fieldNewPassword))
                    .getReadMethod().invoke(o);
            currentPasswordConfirmation = (String) Objects.requireNonNull(BeanUtils.getPropertyDescriptor(o.getClass(), this.fieldNewPasswordConfirmation))
                    .getReadMethod().invoke(o);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            throw new ValidationException(e);
        }

        return currentPassword.equals(currentPasswordConfirmation);
    }
}
