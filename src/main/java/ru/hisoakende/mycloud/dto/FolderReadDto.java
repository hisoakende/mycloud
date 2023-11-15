package ru.hisoakende.mycloud.dto;

import lombok.Builder;
import lombok.Data;
import ru.hisoakende.mycloud.entity.File;
import ru.hisoakende.mycloud.entity.Folder;

import java.util.List;

import java.util.Date;
import java.util.UUID;

@Data
@Builder
public class FolderReadDto implements Dto {

    private UUID uuid;

    private String name;

    private UUID parentFolderId;

    private Date createdAt;

    private Date updatedAt;

    private List<FolderPreviewDto> childFolders;

    private List<FileReadDto> files;

    private Boolean read;

    private Boolean write;

    private Boolean delete;

    private UUID ownerId;

    @Override
    public String toString() {
        return "FolderReadDTO{" + "objectId=" + uuid + ", name='" + name + '\'' + ", parentFolderId=" + parentFolderId + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + '}';
    }
}
