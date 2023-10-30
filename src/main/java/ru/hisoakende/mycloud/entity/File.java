package ru.hisoakende.mycloud.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;


@Entity
@Data
public class File {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID object_id;

    private String name;

    private String path;

    @OneToOne
    @PrimaryKeyJoinColumn
    @JoinColumn(name = "object_id")
    private Object object;
}
