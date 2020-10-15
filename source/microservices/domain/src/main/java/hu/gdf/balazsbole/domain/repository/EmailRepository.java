package hu.gdf.balazsbole.domain.repository;

import hu.gdf.balazsbole.domain.entity.EmailEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;


/**
 * Spring Data  repository for the {@link EmailEntity}.
 */
@Repository
public interface EmailRepository extends JpaRepository<EmailEntity, UUID> {

    @Transactional(readOnly = true)
    Optional<EmailEntity> findByHeader_MessageId(String messageID);

}
