package ru.hisoakende.mycloud.entity;

import jakarta.persistence.*;
import lombok.Data;


import java.util.*;

@Data
@Entity(name = "folder")
public class Folder implements EntityInterface {

    @Id
    @Column(name = "object_id")
    private UUID objectId;

    private String name;

    @Column(name = "folder_id", insertable = false, updatable = false)
    private UUID folderId;

    @ManyToOne
    @JoinColumn(name = "folder_id")
    private Folder folder;

    @OneToMany(mappedBy = "folder")
    private List<Folder> childFolders;

    @OneToMany(mappedBy = "folder")
    private List<File> files;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "object_id")
    private Object object;

    @Override
    public String toString() {
        return "Folder{" +
                "objectId=" + objectId +
                ", name='" + name + '\'' +
                ", folderId=" + folderId +
                ", folder=" + folder +
                ", object=" + object +
                '}';
    }
}
