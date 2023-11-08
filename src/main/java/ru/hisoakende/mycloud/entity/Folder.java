package ru.hisoakende.mycloud.entity;

import jakarta.persistence.*;
import lombok.Data;


import java.io.Serializable;
import java.util.*;

@Data
@Entity(name = "folder")
public class Folder {

    @Id
    @Column(name = "object_id")
    private UUID objectId;

    private String name;

    @Column(name = "parent_folder_id", insertable = false, updatable = false)
    private UUID parentFolderId;

    @ManyToOne
    @JoinColumn(name = "parent_folder_id")
    private Folder parentFolder;

    @OneToMany(mappedBy = "parentFolder")
    private List<Folder> childFolders;

    @OneToMany(mappedBy = "folder")
    private List<File> files;

    @OneToOne
    @JoinColumn(name = "object_id")
    private Object object;

    @Override
    public String toString() {
        return "Folder{" +
                "objectId=" + objectId +
                ", name='" + name + '\'' +
                ", parentFolderId=" + parentFolderId +
                ", parentFolder=" + parentFolder +
                ", object=" + object +
                '}';
    }
}
