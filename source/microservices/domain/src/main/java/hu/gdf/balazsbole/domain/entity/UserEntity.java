package hu.gdf.balazsbole.domain.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Setter
@Entity
@Audited
@Table(name = "users")
@DynamicUpdate
public class UserEntity extends AbstractEntity implements Serializable {

    private static final long serialVersionUID = 3284857033522819183L;

    @NotNull
    @Column(name = "username", nullable = false)
    private String username;

    @NotNull
    @Column(name = "email", nullable = false, updatable = true)
    private String email;

    @JoinColumn(name = "queue_id", nullable = true)
    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY, optional = true)
    private QueueEntity queue;

}
