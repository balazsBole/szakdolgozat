package hu.gdf.balazsbole.domain.entity;

import lombok.Getter;
import lombok.Setter;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import javax.annotation.OverridingMethodsMustInvokeSuper;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * Abstract entity to solve common things:
 * <li>id is uuid</li>
 * <li>id if it's not set, then set it</li>
 * <p>
 * {@code equals()}, {@code hashCode()} are final based on <a href="https://vladmihalcea.com/the-best-way-to-implement-equals-hashcode-and-tostring-with-jpa-and-hibernate/">https://vladmihalcea.com/the-best-way-to-implement-equals-hashcode-and-tostring-with-jpa-and-hibernate/</a>
 * and <a href="https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/">https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/</a>.
 */
@Getter
@Setter
@MappedSuperclass
public class AbstractEntity implements Serializable {

  private static final long serialVersionUID = -1460211229605324421L;

  @Id
  @Column(name = "id", nullable = false, unique = true, updatable = false)
  @Nonnull
  private UUID id;


  /**
   * Setup id if required before persist entity.
   */
  @OverridingMethodsMustInvokeSuper
  @PrePersist
  public void prePersist() {
    //noinspection ConstantConditions
    if (null == id) {
      id = UUID.randomUUID();
    }
  }


  @OverridingMethodsMustInvokeSuper
  @PreUpdate
  public void preUpdate() {
    // DO NOTHING currently
  }


  /**
   * Set ID only if not set yet. If already set, then does nothing. It's required eg. improper mapper config.
   *
   * @param id id.
   */
  @OverridingMethodsMustInvokeSuper
  public void setId(final UUID id) {
    //noinspection ConstantConditions
    if (null == this.id) {
      this.id = id;
    }
  }


  /**
   * Clear id, before you can set it.
   */
  public void clearId() {
    //noinspection ConstantConditions
    this.id = null; //NOSONAR
  }


  @Override
  public final boolean equals(final Object o) {
    if (this == o) {
      return true;
    }

    if (!(o instanceof AbstractEntity)) {
      return false;
    }

    final AbstractEntity other = (AbstractEntity) o;

    //noinspection ConstantConditions
    return id != null && id.equals(other.getId());
  }


  @Override
  public final int hashCode() {
    return 42;
  }


  /**
   * Compare to {@link AbstractEntity} with their IDs.
   *
   * @return {@code true} if references are same; or both value are null; or both IDs are null or equals.
   */
  public static boolean equalsWithId(@CheckForNull final AbstractEntity a, @CheckForNull final AbstractEntity b) {
    if (a == b) {
      return true;
    }

    if (null == a || null == b) {
      return false;
    }
    return Objects.equals(a.getId(), b.getId());
  }


  /**
   * Compare to {@link AbstractEntity} with their IDs.
   *
   * @return {@code true} if both value are null; or both IDs are null or equals.
   */
  public static boolean equalsWithId(@CheckForNull final AbstractEntity a, @CheckForNull final UUID b) {
    if (null == a && null == b) {
      return true;
    }
    if (null == a) {
      return false;
    }
    if (null == b) {
      return true;
    }

    return Objects.equals(a.getId(), b);
  }

}
