package hu.gdf.balazsbole.domain.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
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


    @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<EmailthreadEntity> emailthreads = new ArrayList<>();

}
