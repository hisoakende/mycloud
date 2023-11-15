package ru.hisoakende.mycloud.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;


@Data
public class FolderMovedDto {

    @NotNull
    private UUID folderId;

}
