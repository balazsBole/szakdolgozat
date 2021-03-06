package hu.gdf.balazsbole.domain.entity;

import hu.gdf.balazsbole.domain.enumeration.Status;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Audited
@Table(name = "thread")
@BatchSize(size = 512)
@DynamicUpdate
public class EmailThreadEntity extends AbstractEntity implements Serializable {

    private static final long serialVersionUID = 744405217690804672L;

    @JoinColumn(name = "user_id", nullable = true)
    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY, optional = true)
    private UserEntity user;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status;

    @Column(name = "description")
    private String description;

    @NotAudited
    @OneToMany(mappedBy = "emailThread", cascade = CascadeType.PERSIST, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<EmailEntity> emails = new ArrayList<>();

    @NotNull
    @JoinColumn(name = "queue_id", nullable = false)
    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private QueueEntity queue;
}
