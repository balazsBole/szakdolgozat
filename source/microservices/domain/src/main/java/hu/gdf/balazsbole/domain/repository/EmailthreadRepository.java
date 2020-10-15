package hu.gdf.balazsbole.domain.repository;

import hu.gdf.balazsbole.domain.dto.Emailthread;
import hu.gdf.balazsbole.domain.entity.EmailEntity;
import hu.gdf.balazsbole.domain.entity.EmailthreadEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;


/**
 * Spring Data  repository for the {@link EmailthreadRepository}.
 */
@Repository
public interface EmailthreadRepository extends JpaRepository<EmailthreadEntity, UUID> {


}
