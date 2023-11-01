package ru.hisoakende.mycloud.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Generated;
import java.util.*;


@Entity
@Table(name = "object")
@Data
public class Object {

    @Id
    @Column(name = "uuid", nullable = false, updatable = false)
    @Generated
    private UUID uuid;

    @Column(name = "created_at", insertable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @Generated
    private Date createdAt;

    @Column(name = "updated_at", insertable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @Generated
    private Date updatedAt;

    @Override
    public String toString() {
        return "Object{" +
                "uuid=" + uuid +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
