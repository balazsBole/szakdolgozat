package hu.gdf.balazsbole.domain.repository;

import hu.gdf.balazsbole.domain.entity.EmailThreadEntity;
import hu.gdf.balazsbole.domain.enumeration.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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

    List<EmailThreadEntity> findAllByQueue_IdAndStatus(UUID queueId, Status status);


    @Query(nativeQuery = true,
            value = "select emailthrea0_.id as id1_8_, emailthrea0_.rev as rev2_8_, emailthrea0_.revtype as revtype3_8_, emailthrea0_.description as descript4_8_, emailthrea0_.status as status5_8_, emailthrea0_.queue_id as queue_id6_8_, emailthrea0_.user_id as user_id7_8_ from thread_aud emailthrea0_ where emailthrea0_.id=:id order by emailthrea0_.rev asc"
    )
    List<EmailThreadEntity> getLastModificationApproveForBP(@Param("id") UUID id);


}
