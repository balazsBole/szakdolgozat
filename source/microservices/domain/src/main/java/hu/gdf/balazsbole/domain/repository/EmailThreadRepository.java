package hu.gdf.balazsbole.domain.repository;

import hu.gdf.balazsbole.domain.entity.EmailThreadEntity;
import hu.gdf.balazsbole.domain.enumeration.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;


/**
 * Spring Data repository for the {@link EmailThreadRepository}.
 */
@Repository
public interface EmailThreadRepository extends JpaRepository<EmailThreadEntity, UUID> {

    List<EmailThreadEntity> findAllByUserIsNullAndQueue_Id(UUID queueId);

    List<EmailThreadEntity> findAllByStatusAndUser_KeycloakID(Status status, UUID keycloakId);

}
