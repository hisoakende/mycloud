package entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Generated;

import java.util.Date;
import java.util.UUID;


@Entity
@Data
public class Object {

    @Id
    @Column(nullable = false, updatable = false)
    private UUID uuid;

    @Column(name = "created_at", insertable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @Generated
    private Date createdAt;

    @Column(name = "updated_at", insertable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @Generated
    private Date updatedAt;
}
