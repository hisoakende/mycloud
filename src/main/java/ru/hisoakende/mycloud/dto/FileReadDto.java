package ru.hisoakende.mycloud.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
public class FileReadDto {

    private UUID uuid;

    private String name;

    private String path;

    private Date createdAt;

    private Date updatedAt;
}
