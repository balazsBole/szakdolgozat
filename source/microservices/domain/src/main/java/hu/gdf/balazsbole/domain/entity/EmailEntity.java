package hu.gdf.balazsbole.domain.entity;

import hu.gdf.balazsbole.domain.enumeration.Direction;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "email")
@DynamicUpdate
public class EmailEntity extends AbstractEntity implements Serializable {

    private static final long serialVersionUID = 5072970713595961865L;

    @Column(name = "parent_id")
    private UUID parentId;

    @JoinColumn(name = "thread_id", nullable = false)
    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY, optional = false)
    private EmailThreadEntity emailThread;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "direction", nullable = false)
    private Direction direction;

    @NotNull
    @JoinColumn(name = "header_id", nullable = false)
    @OneToOne(cascade = CascadeType.PERSIST, orphanRemoval = true, fetch = FetchType.LAZY, optional = false)
    private HeaderEntity header;

    @NotNull
    @JoinColumn(name = "content_id", nullable = false)
    @OneToOne(cascade = CascadeType.PERSIST, orphanRemoval = true, fetch = FetchType.LAZY, optional = false)
    private ContentEntity content;

    @NotNull
    @Column(name = "read", nullable = false)
    private boolean read;

    @NotNull
    @Column(name = "processed", nullable = false)
    private LocalDateTime processed;

}
