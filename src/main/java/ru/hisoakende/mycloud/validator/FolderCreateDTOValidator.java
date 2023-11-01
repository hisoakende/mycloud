package ru.hisoakende.mycloud.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.hisoakende.mycloud.dto.FolderCreateDto;
import ru.hisoakende.mycloud.entity.Folder;
import ru.hisoakende.mycloud.exception.EntityNotFoundException;
import ru.hisoakende.mycloud.service.FolderService;
import ru.hisoakende.mycloud.validator.constraint.FolderCreateDTOConstraint;

import java.util.UUID;

@Component
public class FolderCreateDTOValidator implements ConstraintValidator<FolderCreateDTOConstraint, FolderCreateDto> {

    @Autowired
    private FolderService folderService;

    @Override
    public boolean isValid(FolderCreateDto folderCreateDTO, ConstraintValidatorContext constraintValidatorContext) {
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
