package com.algaworks.algafoodapi.core.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.math.BigDecimal;

public class MultipleValidator implements ConstraintValidator<Multiple, Number> {

    private int multipleNumber;

    @Override
    public void initialize(Multiple constraintAnnotation) {
        this.multipleNumber = constraintAnnotation.number();
    }

    @Override
    public boolean isValid(Number value, ConstraintValidatorContext constraintValidatorContext) {
        boolean valid = true;

        if (value != null) {
            BigDecimal decimalValue = BigDecimal.valueOf(value.doubleValue());
            BigDecimal decimalMultiple = BigDecimal.valueOf(multipleNumber);
            BigDecimal remainder = decimalValue.remainder(decimalMultiple);

            valid = BigDecimal.ZERO.compareTo(remainder) == 0;
        }
        return valid;
    }
}