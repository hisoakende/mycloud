package ru.hisoakende.mycloud.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FolderPreviewDto implements Dto {

    private UUID uuid;

    private String name;

    private Date createdAt;

    private Date updatedAt;

}
