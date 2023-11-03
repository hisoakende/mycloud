package ru.hisoakende.mycloud.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;


@Entity
@Data
public class File {

    @Id
    @Column(name = "object_id")
    private UUID objectId;

    private String name;

    private String path;

    @Column(name = "folder_id", insertable = false, updatable = false)
    private UUID folderId;

    @ManyToOne
    @JoinColumn(name = "folder_id")
    private Folder folder;

    @OneToOne
    @JoinColumn(name = "object_id")
    private Object object;
}
