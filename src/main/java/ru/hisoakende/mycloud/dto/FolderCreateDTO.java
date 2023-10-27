package ru.hisoakende.mycloud.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import ru.hisoakende.mycloud.validators.constraint.FolderCreateDTOConstraint;

import java.util.UUID;

@Data
@FolderCreateDTOConstraint
public class FolderCreateDTO {

    @NotNull(message = "Field parentFolderId id should be not null")
    private UUID parentFolderId;

    @NotEmpty(message = "Field name should be not null")
    private String name;

    @Override
    public String toString() {
        return "FolderDTO{" +
                "parentId=" + parentFolderId +
                ", name='" + name + '\'' +
                '}';
    }
}
