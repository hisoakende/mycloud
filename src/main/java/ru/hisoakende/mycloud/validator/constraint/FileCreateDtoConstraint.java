package ru.hisoakende.mycloud.validator.constraint;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import ru.hisoakende.mycloud.validator.FileCreateDtoValidator;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = FileCreateDtoValidator.class)
@Documented
public @interface FileCreateDtoConstraint {
    String message() default "Invalid file";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
