package ru.hisoakende.mycloud.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class FolderPatchDto {

    @NotBlank
    String name;
}