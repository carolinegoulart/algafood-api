package com.algaworks.algafoodapi.core.validation;

import org.springframework.beans.BeanUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.ValidationException;
import java.math.BigDecimal;

public class FreeDeliveryFeeContainsNameValidator implements ConstraintValidator<FreeDeliveryFeeContainsName, Object> {

    private String fieldValue;
    private String descriptionField;
    private String mandatoryDescription;

    @Override
    public void initialize(FreeDeliveryFeeContainsName constraintAnnotation) {
        this.fieldValue = constraintAnnotation.fieldValue();
        this.descriptionField = constraintAnnotation.descriptionField();
        this.mandatoryDescription = constraintAnnotation.mandatoryDescription();
    }

    @Override
    public boolean isValid(Object object, ConstraintValidatorContext constraintValidatorContext) {
        boolean valid = true;

        try {
            BigDecimal value = (BigDecimal) BeanUtils.getPropertyDescriptor(object.getClass(), fieldValue)
                    .getReadMethod().invoke(object);
            String description = (String) BeanUtils.getPropertyDescriptor(object.getClass(), descriptionField)
                    .getReadMethod().invoke(object);

            if (value != null && BigDecimal.ZERO.compareTo(value) == 0 && description != null) {
                valid = description.toLowerCase().contains(this.mandatoryDescription.toLowerCase());
            }
            return valid;

        } catch (Exception e) {
            throw new ValidationException(e);
        }
    }
}