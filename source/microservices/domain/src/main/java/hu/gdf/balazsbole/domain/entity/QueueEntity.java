package hu.gdf.balazsbole.domain.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.envers.Audited;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Setter
@Entity
@Audited
@Table(name = "queue")
@DynamicUpdate
public class QueueEntity extends AbstractEntity implements Serializable {

    private static final long serialVersionUID = 680552792991177201L;

    @NotNull
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @NotNull
    @Column(name = "description")
    private String description;

    @NotNull
    @Column(name = "email", nullable = false, unique = true, updatable = false)
    private String email;


}
