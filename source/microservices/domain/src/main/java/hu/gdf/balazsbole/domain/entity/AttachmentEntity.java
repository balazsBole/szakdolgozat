package hu.gdf.balazsbole.domain.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "attachment")
@DynamicUpdate
public class AttachmentEntity extends AbstractEntity implements Serializable {

    private static final long serialVersionUID = 5072970713595961865L;

    @NotNull
    @JoinColumn(name = "content_id", nullable = false)
    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY, optional= false)
    private ContentEntity content;

    @NotNull
    @Column(name = "data", nullable = false)
    private byte[] data;

    @NotNull
    @Column(name = "content_type", nullable = false)
    private String contentType;

    @NotNull
    @Column(name = "filename", nullable = false)
    private String filename;

}
