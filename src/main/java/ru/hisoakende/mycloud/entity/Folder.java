package ru.hisoakende.mycloud.entity;

import jakarta.persistence.*;
import lombok.Data;


import java.io.Serializable;
import java.util.*;

@Data
@Entity(name = "folder")
public class Folder implements Serializable {

    @Id
    @Column(name = "object_id")
    private UUID objectId;

    private String name;

    @Transient
    private UUID parentFolderId;

    @ManyToOne
    @JoinColumn(name = "parent_folder_id")
    private Folder parentFolder;

    @OneToMany(mappedBy = "parentFolder")
    private List<Folder> childFolders;

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
