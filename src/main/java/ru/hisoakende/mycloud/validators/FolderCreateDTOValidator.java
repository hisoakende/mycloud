package ru.hisoakende.mycloud.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.hisoakende.mycloud.dto.FolderCreateDTO;
import ru.hisoakende.mycloud.entity.Folder;
import ru.hisoakende.mycloud.exceptions.EntityNotFoundException;
import ru.hisoakende.mycloud.services.FolderService;
import ru.hisoakende.mycloud.validators.constraint.FolderCreateDTOConstraint;

import java.util.UUID;

@Component
public class FolderCreateDTOValidator implements ConstraintValidator<FolderCreateDTOConstraint, FolderCreateDTO> {

    @Autowired
    private FolderService folderService;

    @Override
    public void initialize(FolderCreateDTOConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(FolderCreateDTO folderCreateDTO, ConstraintValidatorContext constraintValidatorContext) {
        UUID parentFolderId = folderCreateDTO.getParentFolderId();
        Folder parentFolder;
        try {
            parentFolder = folderService.getById(parentFolderId);
        } catch (EntityNotFoundException e) {
            return false;
        }
        return folderService.isUniqueFolderFromParent(folderCreateDTO.getName(), parentFolder);
    }
}
