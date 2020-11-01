package hu.gdf.balazsbole.domain.repository;

import hu.gdf.balazsbole.domain.entity.QueueEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

/**
 * Spring Data repository for the {@link QueueEntity}.
 */
@Repository
public interface QueueRepository extends JpaRepository<QueueEntity, UUID> {

    @Transactional(readOnly = true)
    Optional<QueueEntity> findByEmail(String email);

}