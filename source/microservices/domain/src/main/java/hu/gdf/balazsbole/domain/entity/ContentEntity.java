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
@Table(name = "content")
@DynamicUpdate
public class ContentEntity extends AbstractEntity implements Serializable {

    private static final long serialVersionUID = -7570132458945857937L;

    @NotNull
    @Column(name = "body", nullable = false)
    private String body;

    @NotNull
    @Column(name = "html", nullable = false)
    private boolean html;

    @OneToMany(mappedBy = "content", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
    private List<AttachmentEntity> attachments = new ArrayList<>();

}
