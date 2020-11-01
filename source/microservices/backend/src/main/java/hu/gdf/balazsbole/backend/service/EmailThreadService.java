package hu.gdf.balazsbole.backend.service;

import hu.gdf.balazsbole.domain.dto.EmailThread;
import hu.gdf.balazsbole.domain.entity.EmailThreadEntity;
import hu.gdf.balazsbole.domain.enumeration.Status;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EmailThreadService {

    @Transactional
    EmailThreadEntity createThreadWith(Status status);

    @Transactional(readOnly = true)
    List<EmailThread> getUnassignedEmailThreads();

    @Transactional(readOnly = true)
    List<EmailThread> findAllByStatusAndKeycloakUser(UUID keycloakId, Status status);

    @Transactional
    void updateStatus(UUID emailThreadId, Status status);

    void updateUser(UUID emailThreadId, UUID userId);

    @Transactional(readOnly = true)
    Optional<EmailThread> findById(UUID emailThreadId);
}
