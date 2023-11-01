package ru.hisoakende.mycloud.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import ru.hisoakende.mycloud.validator.constraint.FolderCreateDTOConstraint;

import java.util.UUID;

@Data
@FolderCreateDTOConstraint
public class FolderCreateDto {

    @NotNull //todo найти способ валидации empty
    private UUID parentFolderId;

    @NotBlank
    private String name;

    @Override
    public String toString() {
        return "FolderDTO{" + "parentId=" + parentFolderId + ", name='" + name + '\'' + '}';
    }
}
