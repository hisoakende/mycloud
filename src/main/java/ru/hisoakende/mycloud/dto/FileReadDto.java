package ru.hisoakende.mycloud.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
public class FileReadDto implements Dto {

    private UUID uuid;

    private String name;

    private String path;

    private UUID folderId;

    private Date createdAt;

    private Date updatedAt;

    private Boolean read = false;

    private Boolean write = false;

    private Boolean delete = false;

    private UUID ownerId;
}
