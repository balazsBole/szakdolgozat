package hu.gdf.balazsbole.domain.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "users")
@DynamicUpdate
public class UserEntity extends AbstractEntity implements Serializable {

    private static final long serialVersionUID = 3284857033522819183L;

    @NotNull
    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "keycloak_id", nullable = false, unique = true, updatable = false)
    @NotNull
    private UUID keycloakID;

    @JoinColumn(name = "queue_id", nullable = true)
    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY, optional = true)
    private QueueEntity queue;

}
