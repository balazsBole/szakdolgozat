package hu.gdf.balazsbole.backend.service;

import hu.gdf.balazsbole.domain.dto.Emailthread;
import hu.gdf.balazsbole.domain.entity.EmailthreadEntity;
import hu.gdf.balazsbole.domain.enumeration.Status;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EmailthreadService {

    @Transactional
    EmailthreadEntity createThreadWith(Status status);

    @Transactional(readOnly = true)
    List<Emailthread> getUnassignedEmailThreads();

    @Transactional(readOnly = true)
    List<Emailthread> findAllByStatusAndKeycloakUser(UUID keycloakId, Status status);

    @Transactional
    Emailthread updateStatus(UUID emailThreadId, Status status);

    Emailthread updateUser(UUID emailThreadId, UUID userId);

    @Transactional(readOnly = true)
    Optional<Emailthread> findById(UUID emailThreadId);
}
