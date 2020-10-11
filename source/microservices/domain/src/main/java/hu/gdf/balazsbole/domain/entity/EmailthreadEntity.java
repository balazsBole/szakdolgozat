package hu.gdf.balazsbole.domain.entity;

import hu.gdf.balazsbole.domain.enumeration.Status;
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
@Table(name = "thread")
@DynamicUpdate
public class EmailthreadEntity  extends AbstractEntity implements Serializable {

    private static final long serialVersionUID = 744405217690804672L;

    @ManyToOne
    @NotNull
    @Column(name = "user_id", nullable = false)
    private UserEntity user;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status_id", nullable = false)
    private Status status;

    @OneToMany(mappedBy = "email", cascade = CascadeType.PERSIST, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<EmailEntity> emails = new ArrayList<>();

}
