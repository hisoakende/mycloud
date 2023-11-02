package ru.hisoakende.mycloud.validator.constraint;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import ru.hisoakende.mycloud.validator.FolderCreateDtoValidator;

import java.lang.annotation.*;


@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = FolderCreateDtoValidator.class)
@Documented
public @interface FolderCreateDtoConstraint {
    String message() default "Invalid folder";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
