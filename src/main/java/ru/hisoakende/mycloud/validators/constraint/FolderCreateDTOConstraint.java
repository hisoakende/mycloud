package ru.hisoakende.mycloud.validators.constraint;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import ru.hisoakende.mycloud.validators.FolderCreateDTOValidator;

import java.lang.annotation.*;


@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = FolderCreateDTOValidator.class)
@Documented
public @interface FolderCreateDTOConstraint {
    String message() default "Invalid folder";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
