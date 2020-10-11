package hu.gdf.balazsbole.domain.repository;

import hu.gdf.balazsbole.domain.dto.Email;
import hu.gdf.balazsbole.domain.entity.EmailEntity;
import hu.gdf.balazsbole.domain.entity.HeaderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;


/**
 * Spring Data  repository for the {@link HeaderEntity}.
 */
@Repository
public interface HeaderRepository extends JpaRepository<HeaderEntity, UUID> {

    @Transactional(readOnly = true)
    Optional<HeaderEntity> findByMessageId(String messageId);

}
