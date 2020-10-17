package hu.gdf.balazsbole.domain.repository;

import hu.gdf.balazsbole.domain.entity.EmailthreadEntity;
import hu.gdf.balazsbole.domain.enumeration.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;


/**
 * Spring Data  repository for the {@link EmailthreadRepository}.
 */
@Repository
public interface EmailthreadRepository extends JpaRepository<EmailthreadEntity, UUID> {

    List<EmailthreadEntity> findAllByUserIsNull();

    List<EmailthreadEntity> findAllByStatusAndUser_Id(Status status, UUID userId);

}
