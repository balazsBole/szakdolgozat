package hu.gdf.balazsbole.domain.repository;

import hu.gdf.balazsbole.domain.entity.EmailThreadAuditEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


/**
 * Spring Data repository for the {@link EmailThreadAuditEntity}.
 */
@Repository
public interface EmailThreadAuditRepository extends JpaRepository<EmailThreadAuditEntity, UUID> {

    List<EmailThreadAuditEntity> findAllByThreadId(UUID threadId);

    @Query(value = "select distinct cast(audit.id as varchar) from thread_aud audit where audit.user_Id=:userId",
            nativeQuery = true
    )
    List<UUID> getThreadIdsRelatedToUser(@Param("userId") UUID userId);

    Optional<EmailThreadAuditEntity> findDistinctFirstByThreadIdOrderByIdDesc(UUID threadId);
}
