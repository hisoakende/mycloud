package ru.hisoakende.mycloud.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class FolderUpdateDto implements Dto {

    @NotBlank
    String name;

    private Boolean read = false;

    private Boolean write = false;

    private Boolean delete = false;
}