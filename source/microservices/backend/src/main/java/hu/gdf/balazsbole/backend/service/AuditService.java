package hu.gdf.balazsbole.backend.service;

import hu.gdf.balazsbole.domain.dto.EmailThread;
import hu.gdf.balazsbole.domain.dto.EmailThreadAudit;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

public interface AuditService {

    @Transactional(readOnly = true)
    List<EmailThreadAudit> historyOfEmailThreadBy(UUID emailThreadId);

    @Transactional(readOnly = true)
    List<EmailThread> emailThreadsRelatedToUser(UUID userId);

    @Transactional(readOnly = true)
    int getLatestVersionFromEmailThread(UUID emailThreadId);
}
