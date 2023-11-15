package ru.hisoakende.mycloud.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class FilePatchDto {

    @NotBlank
    String name;
}
