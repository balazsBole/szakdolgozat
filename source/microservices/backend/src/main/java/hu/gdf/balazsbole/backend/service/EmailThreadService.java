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
    EmailThreadEntity createThreadFor(String email, String description);

    @Transactional(readOnly = true)
    List<EmailThread> getUnassignedEmailThreadsFor(UUID uuid);

    @Transactional(readOnly = true)
    List<EmailThread> findAllByStatusAndUserId(UUID keycloakId, Status status);

    @Transactional(readOnly = true)
    List<EmailThread> findAllByStatusInTheQueueOf(Status status, UUID keycloakId);

    @Transactional
    void update(UUID threadId, EmailThread emailThread);

    @Transactional(readOnly = true)
    Optional<EmailThread> findById(UUID emailThreadId);
}
