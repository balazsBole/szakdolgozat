package hu.gdf.balazsbole.domain.entity;

import hu.gdf.balazsbole.domain.enumeration.Status;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.Nonnull;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "thread_aud")
public class EmailThreadAuditEntity implements Serializable {

    private static final long serialVersionUID = -6077851955141474670L;

    @Id
    @Column(name = "rev", nullable = false, unique = true, updatable = false)
    @Nonnull
    private int id;

    @Column(name = "revtype")
    @Nonnull
    private Byte type;

    @Column(name = "id", nullable = false)
    @Nonnull
    private UUID threadId;

    @JoinColumn(name = "user_id", nullable = true)
    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY, optional = true)
    private UserEntity user;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status;

    @Column(name = "description")
    private String description;

    @NotNull
    @JoinColumn(name = "queue_id", nullable = false)
    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private QueueEntity queue;
}
