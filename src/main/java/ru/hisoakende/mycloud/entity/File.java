package ru.hisoakende.mycloud.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;


@Entity
@Data
public class File {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JoinColumn(name = "object_id")
    private UUID objectId;

    private String name;

    private String path;

}
