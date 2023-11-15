package ru.hisoakende.mycloud.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class FileMovedDto {

    @NotNull
    private UUID folderId;
}
