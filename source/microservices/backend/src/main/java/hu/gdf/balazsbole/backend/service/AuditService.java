package hu.gdf.balazsbole.backend.service;

import hu.gdf.balazsbole.domain.dto.EmailThread;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

public interface AuditService {

    @Transactional(readOnly = true)
    List<EmailThread> historyOfEmailThreadBy(UUID emailThreadId);

    List<EmailThread> currentStatusOfEmailThreadByPreviousOwner(UUID userId);
}
