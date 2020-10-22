package hu.gdf.balazsbole.domain.repository;

import hu.gdf.balazsbole.domain.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;


/**
 * Spring Data  repository for the {@link UserEntity}.
 */
@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID> {

    @Transactional(readOnly = true)
    Optional<UserEntity> findByKeycloakID(UUID keycloakId);

}
