package hu.gdf.balazsbole.domain.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "users")
@DynamicUpdate
public class UserEntity extends AbstractEntity implements Serializable {

    private static final long serialVersionUID = 3284857033522819183L;

    @NotNull
    @Column(name = "username", nullable = false)
    private String  username;

    @OneToMany(mappedBy = "email", cascade = CascadeType.PERSIST, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<EmailEntity> emails = new ArrayList<>();

}
