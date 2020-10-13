package hu.gdf.balazsbole.domain.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "header")
@DynamicUpdate
public class HeaderEntity  extends AbstractEntity implements Serializable {

    private static final long serialVersionUID = -8317235648494896758L;

    @NotNull
    @Column(name = "message_id", nullable = false)
    private String messageId;

    @Column(name = "in_reply_to")
    private String inReplyTo;

    @NotNull
    @Column(name = "from", nullable = false)
    private String  from;

    @NotNull
    @Column(name = "to", nullable = false)
    private String  to;

    @NotNull
    @Column(name = "subject", nullable = false)
    private String subject;

}
