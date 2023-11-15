package ru.hisoakende.mycloud.dto;

import jakarta.validation.GroupSequence;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.hisoakende.mycloud.validator.constraint.FileCreateDtoConstraint;
import ru.hisoakende.mycloud.validator.group.FirstOrder;

import java.util.UUID;

@Data
@GroupSequence({FirstOrder.class, FileCreateDto.class})
@FileCreateDtoConstraint
public class FileCreateDto implements Dto {

    @NotBlank(groups = FirstOrder.class)
    private String name;

    @NotNull(groups = FirstOrder.class)
    private UUID folderId;

    private UUID ownerId;

    private Boolean read = false;

    private Boolean write = false;

    private Boolean delete = false;
}
