package ru.hisoakende.mycloud.dto;

import jakarta.validation.GroupSequence;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import ru.hisoakende.mycloud.validator.constraint.FolderCreateDtoConstraint;
import ru.hisoakende.mycloud.validator.group.FirstOrder;

import java.util.UUID;

@Data
@GroupSequence({FirstOrder.class, FolderCreateDto.class})
@FolderCreateDtoConstraint
public class FolderCreateDto {

    @NotNull(groups = FirstOrder.class) //todo найти способ валидации empty
    private UUID parentFolderId;

    @NotBlank(groups = FirstOrder.class)
    private String name;

    @Override
    public String toString() {
        return "FolderDTO{" + "parentId=" + parentFolderId + ", name='" + name + '\'' + '}';
    }
}
