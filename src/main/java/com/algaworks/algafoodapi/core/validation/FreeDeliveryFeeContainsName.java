package com.algaworks.algafoodapi.core.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = { FreeDeliveryFeeContainsNameValidator.class })
public @interface FreeDeliveryFeeContainsName {

    String message() default "Invalid name";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    String fieldValue();
    String descriptionField();
    String mandatoryDescription();

}