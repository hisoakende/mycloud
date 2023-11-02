package ru.hisoakende.mycloud.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.hisoakende.mycloud.dto.FileCreateDto;
import ru.hisoakende.mycloud.exception.EntityNotFoundException;
import ru.hisoakende.mycloud.service.FolderService;
import ru.hisoakende.mycloud.validator.constraint.FileCreateDtoConstraint;

import java.util.UUID;

@Component
public class FileCreateDtoValidator implements ConstraintValidator<FileCreateDtoConstraint, FileCreateDto> {

    @Autowired
    private FolderService folderService;

    @Override
    public boolean isValid(FileCreateDto fileCreateDto, ConstraintValidatorContext constraintValidatorContext) {
        UUID folderId = fileCreateDto.getFolderId();
        try {
            folderService.getById(folderId);
        } catch (EntityNotFoundException e) {
            return false;
        }
        return true;
    }
}
